package com.onefly.united.common.constant;

import lombok.Data;

import java.util.Map;

@Data
public class LogMessageDto {

    //01 登录 02 操作 03 错误
    private String logType;
    //数据
    private Map<String, Object> data;
}
