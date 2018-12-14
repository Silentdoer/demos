package me.silentdoer.springbootcontrolleradvice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TODO 自定义的异常如果有@ResponseStatus注解，那么当Controller里抛出了这个异常，SpringMVC会自动填写这里的数据给前端，因此这个异常一般不要被全局捕获，否则功能上有一定冲突；
 * @author liqi.wang
 * @version 1.0
 */
//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "业务异常")
public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}
