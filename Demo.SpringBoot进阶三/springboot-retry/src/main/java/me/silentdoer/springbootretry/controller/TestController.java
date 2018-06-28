package me.silentdoer.springbootretry.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootretry.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@RestController
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/test1")
    public String test1(){
        // doService是@Retryable，但是它是同步方法不像@Async一样
        this.testService.doService(3L);
        return "OK";
    }
}
