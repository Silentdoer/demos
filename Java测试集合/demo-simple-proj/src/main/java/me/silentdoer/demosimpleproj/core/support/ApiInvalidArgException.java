package me.silentdoer.demosimpleproj.core.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/9/2018 7:40 PM
 */
public class ApiInvalidArgException extends RuntimeException {

    public ApiInvalidArgException() {
        super();
    }

    public ApiInvalidArgException(String msg) {
        super(msg);
    }
}
