package me.silentdoer.springbootpropertiesvalue.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 2018/6/27 15:29
 */
@Slf4j
@RestController
public class TestController2 {

    @Resource
    private Environment environment;

    @GetMapping("test41")
    public String test41() {
        return this.environment.getProperty("config.datasource.name") + "MMM";
    }
}
