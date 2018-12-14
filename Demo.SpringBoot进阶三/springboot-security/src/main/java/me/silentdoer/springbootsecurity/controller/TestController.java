package me.silentdoer.springbootsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {
    /**
     * 添加了spring-boot-starter-security的pom依赖后如果没有提供正确的Authorization的header那么会返回给对方：
     * {"timestamp":"2018-05-21T06:50:22.877+0000","status":401,"error":"Unauthorized","message":"Unauthorized","path":"/test1"}
     * @return
     */
    @GetMapping("/test1")
    public String test1(){
        return "HHHH中文";
    }
}
