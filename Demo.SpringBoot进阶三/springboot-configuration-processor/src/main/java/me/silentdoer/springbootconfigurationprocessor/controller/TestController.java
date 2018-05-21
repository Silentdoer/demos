package me.silentdoer.springbootconfigurationprocessor.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootconfigurationprocessor.service.TestService;
import me.silentdoer.springbootconfigurationprocessor.service.TestService2;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private TestService2 testService2;

    @GetMapping("/test1")
    public String test1(){
        return this.testService.getFoo();
    }
    @GetMapping("/test2")
    public String test2(){
        return this.testService2.getUu();
    }
}
