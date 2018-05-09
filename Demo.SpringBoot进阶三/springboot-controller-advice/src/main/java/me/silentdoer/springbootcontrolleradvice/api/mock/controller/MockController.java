package me.silentdoer.springbootcontrolleradvice.api.mock.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcontrolleradvice.api.AppResponse;
import me.silentdoer.springbootcontrolleradvice.api.enumerate.AppResponseEnum;
import me.silentdoer.springbootcontrolleradvice.api.mock.service.MockService;
import me.silentdoer.springbootcontrolleradvice.api.model.FooModel;
import me.silentdoer.springbootcontrolleradvice.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0
 */
@RestController
@Slf4j
public class MockController {
    @Autowired
    private MockService mockService;

    @GetMapping("/test1")
    public String test1(){
        return "123中文456";
    }

    @GetMapping("/test2")
    public AppResponse<FooModel> test2(@RequestParam("registerCaptcha") String registerCaptcha){
        if(StringUtils.isBlank(registerCaptcha)){
            // TODO 这一部分也测试成功，确实被GlobalExceptionHandler捕获到了
            throw new BusinessException("验证码不能为空");
        }
        // TODO 这一部分测试成功
        return AppResponseEnum.SUCCESS.getResult(new FooModel().setName("silentdoer").setGender('m'));
    }

    /**
     * service层的异常也能捕获，因为它是由Controller内来调用的
     *
     * @param uid
     * @return
     */
    @GetMapping("/test3")
    public AppResponse<FooModel> test3(@RequestParam("uid") String uid){
        // TODO 测试不是直接由mapping方法抛出的异常是否也能被GlobalExceptionHandler捕获
        // TODO 经过测试可以
        return mockService.doService(uid);
    }
}
