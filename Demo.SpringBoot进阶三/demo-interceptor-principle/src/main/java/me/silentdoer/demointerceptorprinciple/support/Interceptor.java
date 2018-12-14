package me.silentdoer.demointerceptorprinciple.support;

import javax.annotation.Nonnull;

/**
 * 根据迪米特法则来讲，其实客户端根本不需要了解TargetProxy类。将绑定逻辑放到拦截器内部，客户端只需要和拦截器打交道就可以了。
 * 拦截器则是由客户通过实现Interceptor接口来实现具体的拦截规则
 *
 * 由于这里需要通过注解来配置此拦截器的拦截策略，因此不能是用lambda表达式生成，因此也取消了register的default
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 5:29 PM
 */
public interface Interceptor {

    Object intercept(@Nonnull MethodInvocation invocation) throws Exception;

    Object register(@Nonnull Object target);
}
