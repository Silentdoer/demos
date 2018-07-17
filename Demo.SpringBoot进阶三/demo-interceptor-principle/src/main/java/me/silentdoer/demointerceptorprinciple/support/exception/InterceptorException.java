package me.silentdoer.demointerceptorprinciple.support.exception;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.io.UncheckedIOException;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 6:23 PM
 */
public class InterceptorException extends RuntimeException {

    private InterceptorException(String message) {
        super(message);
    }

    public static InterceptorException newException(String message) {
        return new InterceptorException(message);
    }
}
