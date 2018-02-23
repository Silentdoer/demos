package me.silentdoer.simulatespring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-23 16:39
 */
public class PrimitiveParser {
    public static  <T> T parse(String type, Object origin){
        Logger logger = LoggerFactory.getLogger("myLogger");
        if(logger.isDebugEnabled()){
            logger.debug(String.format("%s, %s", type, origin));
        }
        Object result = null;
        switch(type){
            case "long":
            case "java.lang.Long":
                result = Long.parseLong(origin.toString());
                break;
            // etc.
            default:
                throw new UnsupportedOperationException("暂不支持");
        }
        return (T) result;
    }
}
