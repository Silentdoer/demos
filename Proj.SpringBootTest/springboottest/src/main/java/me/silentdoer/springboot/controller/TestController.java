package me.silentdoer.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/12/18 8:02 PM
 */
@RestController
public class TestController {

    // RESTFul api with get method.
    @GetMapping("/hello")  // 可以理解为RequestMethod.GET的RequestMapping，这里默认就是返回字符串而不需要viewResolver，如果需要要配置pom和application.properties
    public String hello(){
        return "hello.";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "hello2,sss";
    }
}
