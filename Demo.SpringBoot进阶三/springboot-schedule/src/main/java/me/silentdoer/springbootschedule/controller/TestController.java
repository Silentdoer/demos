package me.silentdoer.springbootschedule.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping(name = "测试")
public class TestController {

    @GetMapping("/test1")
    public String test1(){
        return "么么么么么么么";
    }
}
