package com.onefly.united.common.exception;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Exception工具类
 *
 * @author Mark Rundon
 */
public class ExceptionUtils {

    /**
     * 获取异常信息
     * @param ex  异常
     * @return 返回异常信息
     */
    public static String getErrorStackTrace(Exception ex) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw, true);
            ex.printStackTrace(pw);
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e) {

            }
            try {
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException e) {

            }
        }

        return sw.toString();
    }

    /**
     * 转换成 {@link Map <String,Object>} 对象，可用于转换成{@literal JSON}.
     *
     * @param message 消息
     * @return 返回 {@link Map<String,Object>} 对象
     */
    public static Map<String, Object> map(String message) {
        return ImmutableMap.of(
                "msg", Strings.nullToEmpty(message)
        );
    }

    public static Map<String, Object> map(String message, String... args) {
        return ImmutableMap.of(
                "msg", Strings.nullToEmpty(message),
                "args", args == null ? "" : args
        );
    }

    /**
     * 转换成 {@link Map<String,Object>} 对象，可用于转换成{@literal JSON}.
     *
     * @param message 消息
     * @param status    组成消息的参数
     * @return 返回 {@link Map<String,Object>} 对象
     */
    public static Map<String, Object> map(int status, String message) {
        return ImmutableMap.of(
                "msg", Strings.nullToEmpty(message),
                "code", status
        );
    }

    /**
     * 转换成 {@link Map<String,Object>} 对象，可用于转换成{@literal JSON}.
     *
     * @param e 异常对象引用
     * @return 返回 {@link Map<String,Object>} 对象
     */
    public static Map<String, Object> map(Throwable e) {
        return map(e, false);
    }

    /**
     * 转换成 {@link Map<String,Object>} 对象，可用于转换成{@literal JSON}.
     *
     * @param e         异常对象引用
     * @param withCause 是否包含详细信息
     * @return 返回 {@link Map<String,Object>} 对象
     */
    public static Map<String, Object> map(Throwable e, boolean withCause) {
        if (withCause) {
            return ImmutableMap.of(
                    "code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "msg", format(e),
                    "causalChain", Throwables.getCausalChain(e).stream()
                            .flatMap(item -> Stream.of(item.getStackTrace()))
                            .filter(item -> item.getClassName().indexOf("com.utaka") > -1)
                            .toArray()
            );
        } else {
            return ImmutableMap.of(
                    "code", e.getClass().getName(),
                    "msg", format(e)
            );
        }
    }


    /**
     * 转换成 {@link Map<String,Object>} 对象，可用于转换成{@literal JSON}.
     *
     * @param e 业务异常引用
     * @return 返回 {@link Map<String,Object>} 对象
     */
    public static Map<String, Object> map(RenException e) {
        return ImmutableMap.of(
                "code", e.getCode(),
                "msg", format(e),
                "args", e.getErrorMessageArgs() == null ? "" : e.getErrorMessageArgs()
        );
    }

    private static String format(Throwable e) {
        return Strings.nullToEmpty(e.getMessage()).replace(' ', '_').toLowerCase();
    }
}