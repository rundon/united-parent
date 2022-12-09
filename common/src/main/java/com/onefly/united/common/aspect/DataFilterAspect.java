package com.onefly.united.common.aspect;

import com.onefly.united.common.annotation.DataFilter;
import com.onefly.united.common.constant.Constant;
import com.onefly.united.common.exception.ErrorCode;
import com.onefly.united.common.exception.RenException;
import com.onefly.united.common.interceptor.DataScope;
import com.onefly.united.common.user.SecurityUser;
import com.onefly.united.common.user.UserDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 数据过滤，切面处理类
 *
 * @author Mark Rundon
 */
@Aspect
@Component
public class DataFilterAspect {

    @Pointcut("@annotation(com.onefly.united.common.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) {
        Object params = point.getArgs()[0];
        if (params != null && params instanceof Map) {
            UserDetail user = SecurityUser.getUser();

            //如果是超级管理员，则不进行数据过滤
            if (user.getSuperAdmin() == SuperAdminEnum.YES.value()) {
                return;
            }

            try {
                //否则进行数据过滤
                Map map = (Map) params;
                DataFilter sqlFilter = getSqlFilter(point);
                map.put(Constant.SQL_FILTER, new DataScope(user, sqlFilter));
            } catch (Exception e) {

            }

            return;
        }

        throw new RenException(ErrorCode.DATA_SCOPE_PARAMS_ERROR);
    }

    /**
     * 获取数据过滤的SQL
     */
    private DataFilter getSqlFilter(JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);
        return dataFilter;
    }
}