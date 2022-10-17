/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.onefly.united.common.exception;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.onefly.united.common.constant.Constant;
import com.onefly.united.common.constant.LogMessageDto;
import com.onefly.united.common.utils.HttpContextUtils;
import com.onefly.united.common.utils.IpUtils;
import com.onefly.united.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@RestControllerAdvice
public class RenExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RenExceptionHandler.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RenException.class)
    public Result handleRenException(RenException ex) {
        Result result = new Result();
        result.error(ex.getCode(), ex.getMsg());

        return result;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
        Result result = new Result();
        result.error(ErrorCode.DB_RECORD_EXISTS);

        return result;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException ex) {
        Result result = new Result();
        result.error(ErrorCode.FORBIDDEN,ex.getMessage());

        return result;
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        saveLog(ex);

        return new Result().error(ex.getMessage());
    }

    /**
     * 保存异常日志
     */
    private void saveLog(Exception ex) {
        LogMessageDto log = new LogMessageDto();
        log.setLogType("03");
        Map<String, Object> data = Maps.newHashMap();
        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        data.put("ip", IpUtils.getIpAddr(request));
        data.put("userAgent", request.getHeader(HttpHeaders.USER_AGENT));
        data.put("requestUri", request.getRequestURI());
        data.put("requestMethod", request.getMethod());
        Map<String, String> params = HttpContextUtils.getParameterMap(request);
        if (MapUtil.isNotEmpty(params)) {
            data.put("requestParams", JSON.toJSONString(params));
        }
        //异常信息
        data.put("errorInfo", ExceptionUtils.getErrorStackTrace(ex));
        log.setData(data);
        redisTemplate.convertAndSend(Constant.LOG_CHANNEL_TOPIC, JSON.toJSONString(log));
    }
}