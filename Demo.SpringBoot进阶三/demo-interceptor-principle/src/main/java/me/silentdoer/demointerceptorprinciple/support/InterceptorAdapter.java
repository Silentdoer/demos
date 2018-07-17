package me.silentdoer.demointerceptorprinciple.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 5:54 PM
 */
public abstract class InterceptorAdapter implements Interceptor {

    /**
     * 这里确实可以简化MethodInvocation这个类而直接用Object target, Method method, Object[] args这三个参数代替
     */
    @Override
    public Object intercept(@Nonnull MethodInvocation invocation) throws Exception {
        return invocation.proceed();
    }

    @Override
    public Object register(@Nonnull Object target) {
        return TargetProxy.bind(target, this);
    }
}
