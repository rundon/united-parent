/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.onefly.united.common.user;

import com.alibaba.fastjson.JSON;
import com.onefly.united.common.exception.RenException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SecurityUser {

    public static Object getSubject() {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        try {
            if (authentication.getUserAuthentication().getPrincipal() instanceof UserDetail) {
                return authentication.getUserAuthentication().getPrincipal();
            } else {
                if (authentication.getUserAuthentication().getDetails() instanceof LinkedHashMap) {
                    LinkedHashMap map = (LinkedHashMap) authentication.getUserAuthentication().getDetails();
                    map.put("password", "");
                    return JSON.parseObject(JSON.toJSONString(map), UserDetail.class);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail subject = (UserDetail) getSubject();
        if (subject == null) {
            throw new RenException("无法查询到登录信息");
        }
        return subject;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUser().getId();
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return getUser().getDeptId();
    }
}