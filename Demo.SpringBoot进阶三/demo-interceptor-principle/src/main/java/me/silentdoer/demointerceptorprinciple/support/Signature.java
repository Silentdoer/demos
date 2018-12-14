package me.silentdoer.demointerceptorprinciple.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当@Target({})时，表示这个注解只能是另一个注解的成员
 * 此注解是用来精确定义一个拦截策略；注意，还可以扩展Intercepts中的成员，然后再定义另一个通过EL来匹配的类似@Signature的注解实现模糊匹配
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 6:00 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Signature {

    /**
     * 需要intercept的方法的所属对象的父接口
     */
    Class<?> targetInterfaceType();

    /**
     * 需要拦截的方法的名字
     */
    String methodName();

    /**
     * 需要拦截的方法的参数，通过这三个成员来唯一确定一个要拦截的实例方法
     */
    Class<?>[] argTypes();
}
