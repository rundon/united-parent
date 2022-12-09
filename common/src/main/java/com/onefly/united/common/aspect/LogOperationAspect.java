package com.onefly.united.common.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.onefly.united.common.annotation.LogOperation;
import com.onefly.united.common.constant.LogMessageDto;
import com.onefly.united.common.constant.OperationStatusEnum;
import com.onefly.united.common.redis.RedisMqUtil;
import com.onefly.united.common.user.SecurityUser;
import com.onefly.united.common.user.UserDetail;
import com.onefly.united.common.utils.HttpContextUtils;
import com.onefly.united.common.utils.IpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志，切面处理类
 *
 * @author Mark Rundon
 */
@Aspect
@Component
public class LogOperationAspect {

    @Pointcut("@annotation(com.onefly.united.common.annotation.LogOperation)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            //执行方法
            Object result = point.proceed();

            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.SUCCESS.value());

            return result;
        } catch (Exception e) {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.FAIL.value());

            throw e;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time, Integer status) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        LogOperation annotation = method.getAnnotation(LogOperation.class);
        LogMessageDto log = new LogMessageDto();
        log.setLogType("02");
        Map<String, Object> data = Maps.newHashMap();
        if (annotation != null) {
            //注解上的描述
            data.put("operation", annotation.value());
        }

        //登录用户信息
        UserDetail user = SecurityUser.getUser();
        if (user != null) {
            data.put("creatorName", user.getUsername());
        }
        data.put("status", status);
        data.put("requestTime", (int) time);

        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        data.put("ip", IpUtils.getIpAddr(request));
        data.put("userAgent", request.getHeader(HttpHeaders.USER_AGENT));
        data.put("requestUri", request.getRequestURI());
        data.put("requestMethod", request.getMethod());
        data.put("createDate", new Date());
        //请求参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args[0]);
            data.put("requestParams", params);
        } catch (Exception e) {

        }
        log.setData(data);
        RedisMqUtil.addQueueTask(JSON.toJSONString(log));
    }
}