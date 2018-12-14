package me.silentdoer.demointerceptorprinciple.support.interceptor;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demointerceptorprinciple.support.*;

import javax.annotation.Nonnull;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 7:11 PM
 */
@Intercepts(
        {
                // TODO 其实感觉这个targetType似乎也可以不要，因为一个interceptor肯定是用来配置拦截一个类的，这里之所以存在
                // 是当一个target实现了多个接口时，用于指定只是拦截某些接口
                @Signature(targetInterfaceType = PluginInterface.class, methodName = "execute2", argTypes = {Long.class})
        }
)
@Slf4j
public class TestInterceptor extends InterceptorAdapter {

    @Override
    public Object intercept(@Nonnull MethodInvocation invocation) throws Exception {
        // pre的逻辑
        Object[] args = invocation.getArgs();
        log.info("FLAG:args length:{}", args.length);
        if (args.length > 0) {
            boolean args0IsInstanceOfLong = Long.class.isInstance(args[0]) || long.class.isInstance(args[0]);
            if (args[0] != null && args0IsInstanceOfLong) {
                log.info(args[0].toString());
                args[0] = (Long) args[0] + 107;
                log.info(args[0].toString());
            }
        }
        return invocation.proceed(args);
    }
}
