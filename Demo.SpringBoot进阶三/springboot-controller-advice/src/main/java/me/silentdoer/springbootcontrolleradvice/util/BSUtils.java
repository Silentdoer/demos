package me.silentdoer.springbootcontrolleradvice.util;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcontrolleradvice.exception.BusinessException;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Slf4j
public final class BSUtils {

    public void isTrue(boolean expression, String error){
        if(!expression){
            throw new BusinessException(error);
        }
    }
}
