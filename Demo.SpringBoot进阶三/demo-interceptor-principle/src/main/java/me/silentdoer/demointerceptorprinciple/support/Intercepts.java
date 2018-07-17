package me.silentdoer.demointerceptorprinciple.support;

import java.lang.annotation.*;

/**
 * 这个就类似Mybatis的@Intercepts，这个还可以通过配置到.xml文件里（对应一个Interceptor的对象）来为Interceptor对象定义规则
 * 规定：如果没有此注解的InterceptorImpl则直接抛异常，因为这个注解是有不拦截任何方法的语义存在的；
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 5:59 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {

    Signature[] value() default {};
}
