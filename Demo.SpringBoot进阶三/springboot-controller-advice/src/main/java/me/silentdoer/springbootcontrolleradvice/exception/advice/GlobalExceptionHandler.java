package me.silentdoer.springbootcontrolleradvice.exception.advice;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcontrolleradvice.api.AppResponse;
import me.silentdoer.springbootcontrolleradvice.api.enumerate.AppResponseEnum;
import me.silentdoer.springbootcontrolleradvice.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 还有个@ControllerAdvice，这两个注解都类似@Configuration一样会将此类装载到Spring容器里
 *
 * TODO 这个RestControllerAdvice其实可以理解为@RestController，而@ExceptionHandler可以理解为@RequestMapping
 * TODO 因此@ControllerAdvice又和@Controller对应，而此时如果需要返回字符串那么就需要在@ExceptionHandler上加@ResponseBody
 *
 * @author liqi.wang
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice  // TODO 作为bean
public class GlobalExceptionHandler {

    /**
     * 处理基础异常，包括Exception和其所有子异常，但是这里也是存在精确匹配的情形
     * 这个Exception.class在这里可以理解为 其他异常
     *
     * @return
     */
    @ExceptionHandler({Exception.class})
    AppResponse<String> handleException(Exception ex){  // TODO 数据一样是要返回给前端的
        log.error(ex.toString());
        return AppResponseEnum.SYSTEM_ERROR.getResult(ex.getMessage());
        //return new AppResponse<String>().setCode(2001).setMsg("Other Exception").setData(ex.getMessage());
    }

    /**
     * 如果产生的异常的类型是BusinessException那么根据精确匹配原则将由这个方法来处理
     * TODO @RestController中抛出的BusinessException异常经过测试确实在这里捕获到了
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    AppResponse<String> handleBusinessException(BusinessException ex){
        // TODO 根据严重情况来决定是否用warn
        log.error(ex.toString());
        // TODO 经过测试这个异常确实会被作为返回值返回给前端
        return AppResponseEnum.SERVICE_ERROR_ALERT.getResult(ex.getMessage());
        //return new AppResponse<String>().setCode(4001).setMsg("Other Exception").setData(ex.getMessage());
    }
}
