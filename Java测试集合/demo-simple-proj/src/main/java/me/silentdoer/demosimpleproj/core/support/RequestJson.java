package me.silentdoer.demosimpleproj.core.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/13/2018 9:48 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestJson {

    /**
     * 编码方式，默认base64
     * @return
     */
    String encoder() default "base64";
}
