package me.silentdoer.springbootvalidannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被此注解的Mapping方法的参数只能是数字字符串
 * @author silentdoer
 * @version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJustNumberValid {
    String message() default "数据错误";
}
