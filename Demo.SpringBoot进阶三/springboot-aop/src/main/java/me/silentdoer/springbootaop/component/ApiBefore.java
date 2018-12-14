package me.silentdoer.springbootaop.component;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * TODO 据说@Order是注解在@Aspect的类上的
 * @author liqi.wang
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Documented
public @interface ApiBefore {
    Class<?>[] value() default {};
}
