package com.onefly.united.common.service;


@FunctionalInterface
public interface IAction<T> {

    /**
     * 执行回调
     *
     * @param param
     */
    void run(T param);
}

