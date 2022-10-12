package com.onefly.united.common.config;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Callable;

public final class DelegatingSecurityContextCallable<V> implements Callable<V> {
    // 要代理的 Callable
    private final Callable<V> delegate;
    // 主线程中的 SecurityContext
    private final SecurityContext delegateSecurityContext;
    // 子线程中的 SecurityContext
    private SecurityContext originalSecurityContext;

    // 构造方法传入要代理的 Callable 和主线程中的 SecurityContext
    public DelegatingSecurityContextCallable(Callable<V> delegate, SecurityContext securityContext) {
        this.delegate = delegate;
        this.delegateSecurityContext = securityContext;
    }

    // 构造方法传入要代理的 Callable 和主线程中的 SecurityContext
    public DelegatingSecurityContextCallable(Callable<V> delegate) {
        // 保存主线程的 SecurityContext
        this(delegate, SecurityContextHolder.getContext());
    }

    @Override
    public V call() throws Exception {
        // 暂存子线程的 SecurityContext
        this.originalSecurityContext = SecurityContextHolder.getContext();
        try {
            // 将主线程的 SecurityContext 设置到子线程中
            SecurityContextHolder.setContext(delegateSecurityContext);
            // 调用原始的 Callable
            return delegate.call();
        } finally {
            // 重置为原子线程的 SecurityContext
            SecurityContextHolder.setContext(originalSecurityContext);
        }
    }
}
