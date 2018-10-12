package me.silentdoer.demosimpleproj.core.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/30/2018 5:47 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequiresLogin {
}
