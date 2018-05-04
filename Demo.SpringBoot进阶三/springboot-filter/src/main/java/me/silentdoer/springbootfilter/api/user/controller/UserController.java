package me.silentdoer.springbootfilter.api.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootfilter.api.ApiResult;
import me.silentdoer.springbootfilter.api.user.model.LoginModel;
import me.silentdoer.springbootfilter.api.user.req.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@RestController("mockController")
@RequestMapping("/user")
public class UserController {

    /**
     * `@Valid`有点像@Resource，是Spring支持的一个标准的注解，一般会和@NotBlank、@NotNull、@NotEmpty一起用，SpringMVC会判断参数上有@Valid注解然后会进一步判断这个对象
     * 对应的类或其成员上的注解，比如字段上的@NotBlank注解
     *
     * 这里一个@Valid的参数后必须紧挨着一个BindingResult 参数，否则spring会在校验不通过时直接抛出异常
     * @param loginRequest
     * @param httpSession
     * @param bindingResult
     * @return
     */
    @GetMapping("/login")
    public ApiResult<LoginModel> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors();
            log.debug("Exception occur");
        }

        return null;
    }
}
