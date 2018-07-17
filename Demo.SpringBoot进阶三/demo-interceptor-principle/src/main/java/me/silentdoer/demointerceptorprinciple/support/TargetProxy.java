package me.silentdoer.demointerceptorprinciple.support;

import lombok.var;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demointerceptorprinciple.support.method.MethodSignature;
import me.silentdoer.demointerceptorprinciple.support.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * 用户并不需要TargetProxy的存在（对于Mybatis而言它是Plugin）
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 5:22 PM
 */
@Slf4j
public class TargetProxy implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    /**
     * 需要拦截的方法签名，默认拦截列表为空，默认不拦截任何实例方法
     */
    private final List<MethodSignature> strategies = new LinkedList<>();

    private TargetProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 当调用的方法并没有参数时，args会是null而非Object[0]
     */
    @Override
    public Object invoke(Object proxy, Method method, @Nullable Object[] args) throws Throwable {

        // when method is no args, args is null not Object[0]
        log.info("FLAG:args is null:{}", args == null);

        boolean needIntercept = false;
        for (var signature : this.strategies) {
            needIntercept = signature.getTargetType().isAssignableFrom(this.target.getClass())
                    && Objects.equals(signature.getMethodName(), method.getName())
                    && ClassUtils.equals(((Class<?>[]) signature.getMethodArgTypes().toArray()), method.getParameterTypes());
        }

        if (needIntercept) {
            log.info("FLAG need intercept");
            return this.interceptor.intercept(MethodInvocation.newInstance(this.target, method, args));
        }
        return method.invoke(this.target, args);
    }

    /**
     * 只能通过bind来产生TargetProxy（build）
     * 在bind时已经创建了Interceptor对象，因此可以在这里初始化策略
     */
    static Object bind(@Nonnull Object target, @Nonnull Interceptor interceptor) {

        TargetProxy proxy = new TargetProxy(target, interceptor);

        // 现在开始支持注解和.xml两总策略配置，因此取消原本的没有@Intercepts就认为是不合法的Interceptor的规定
        Intercepts intercepts = interceptor.getClass().getAnnotation(Intercepts.class);
        if (Objects.nonNull(intercepts)) {
            Signature[] signatures = intercepts.value();
            for (var signature : signatures) {
                MethodSignature methodSignature = MethodSignature.builder().build();
                // TODO 这里需要做参数验证（但是不需要做null校验，因为注解成员本身就不支持null）
                // TODO 要校验Signature的targetInterfaceType是否是 target 的父接口之一，否则将跟target毫无关系的接口类型
                // 或父类类型作为type添加到strategies是无意义的
                methodSignature.setTargetType(signature.targetInterfaceType());
                methodSignature.setMethodName(signature.methodName());
                methodSignature.setMethodArgTypes(Arrays.asList(signature.argTypes()));
                // 添加在注解上配置的策略
                proxy.strategies.add(methodSignature);
            }
        }

        // .xml配置的策略，需要参数验证
        /*if (存在.xml配置的策略) {
            读取xml文件并解析出策略；
            添加策略到proxy里
        }*/

        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                                      target.getClass().getInterfaces(), proxy);
    }
}
