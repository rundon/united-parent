package com.onefly.united.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author Mark Rundon
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {

	String value() default "";
}
