package me.silentdoer.springbootvalidannotation.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootvalidannotation.annotation.MyJustNumberValid;
import me.silentdoer.springbootvalidannotation.model.Student;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author silentdoer
 * @version 1.0
 */
@RestController
@Slf4j
public class MockController {

    /**
     * `@ModelAttribute`的用法：先从model去获取key为"user"的对象
     * ,如果获取不到会通过反射实例化一个User对象,再从request里面拿值set到这个对象,然后把这个User对象添加到model(其中key为"user").
     *
     * 如果有@Valid，但是参数紧跟着没有BindingResult参数那么不合法后SpringMVC会直接抛出异常给前端，如果有那么SpringMVC不会抛异常而是由用户通过此对象来主动处理
     *
     * 可以只提供student部分参数，tmp没有
     */
    @GetMapping("/test1")
    public String test1(@ModelAttribute @Valid Student student, BindingResult bindingResult, String tmp){  // TODO 注意BindingResult必须紧跟着@Valid否则认为没有
        /**
         * Stream<Stream<Character>> result = words.map(w -> characterStream(w));  // TODO 将每个元素转换为了一个新数组
         * Stream<Character> letters = words.flatMap(w -> characterStream(w));  // TODO 将每个元素转换为了一个新数组，但最终还是会将这些子数组合并
         */
        
        log.info(StringUtils.join("存在不合规定的数据：", bindingResult.getAllErrors().stream().map(o -> o.toString()).collect(Collectors.toList()).toString()));
        log.info(student.toString());
        // TODO 注意，如果通过@Pattern+@Valid+BindingResult联合使得规定产生效果，但是仍然需要用bindingResult来获取是否存在不合法的数据，因此这里需要用bindingResult来判断
        if(bindingResult.getAllErrors().size() > 0){
            log.debug(StringUtils.join("存在不合法的数据，请处理"));
            //throw new IllegalArgumentException("不合法的参数");
        }
        return "Ok";
    }

    @GetMapping("/test2")
    public String test2(@MyJustNumberValid(message = "字符串中只允许包含数字") String validData){
        return "OK";
    }
}
