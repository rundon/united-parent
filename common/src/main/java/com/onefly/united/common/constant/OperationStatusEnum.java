package com.onefly.united.common.constant;

/**
 * 操作状态枚举
 *
 * @author Mark Rundon
 * @since 1.0.0
 */
public enum OperationStatusEnum {
    /**
     * 失败
     */
    FAIL(0),
    /**
     * 成功
     */
    SUCCESS(1);

    private int value;

    OperationStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}