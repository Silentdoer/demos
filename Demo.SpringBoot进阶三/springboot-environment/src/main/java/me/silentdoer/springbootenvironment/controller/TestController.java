package me.silentdoer.springbootenvironment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/2/2018 3:38 PM
 */
@RestController
@Slf4j
@PropertySource(value = "classpath:config.properties")
public class TestController {

    @Autowired
    private Environment environment;

    @GetMapping("/test")
    public String test() {
        // 可以获取到
        //return environment.getProperty("test.aaa");
        // 在没有@PropertySource(value = "classpath:config.properties")情况下获取不到，但不会产生异常只是返回null
        // 有@PropertySource(value = "classpath:config.properties")情况下获取能获取到
        String property = environment.getProperty("sss.ttt");
        log.info("{}", property == null);
        return property;
    }
}
