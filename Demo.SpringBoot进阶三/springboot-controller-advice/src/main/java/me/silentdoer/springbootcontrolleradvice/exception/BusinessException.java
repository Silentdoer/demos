package me.silentdoer.springbootcontrolleradvice.exception;

/**
 * @author liqi.wang
 * @version 1.0
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}
