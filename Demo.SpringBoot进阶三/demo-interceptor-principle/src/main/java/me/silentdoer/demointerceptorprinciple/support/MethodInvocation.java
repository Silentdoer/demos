package me.silentdoer.demointerceptorprinciple.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 真正执行被拦截方法的地方
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 5:23 PM
 */
@Data
public class MethodInvocation {

    private Object target;

    private Method method;

    private Object[] args;

    private MethodInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    static MethodInvocation newInstance(@Nonnull Object target, @Nonnull Method method, @Nullable Object[] args) {
        return new MethodInvocation(target, method, args == null ? new Object[0] : args);
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(this.target, this.args);
    }

    /**
     * 这个方法是为了能让客户创建自定义Interceptor时可以对参数进行一些修改，先通过invocation.getArgs()获取原值然后进行一定修改
     */
    public Object proceed(@Nonnull Object[] args) throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(this.target, args);
    }
}