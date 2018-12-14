package me.silentdoer.springboothttpstest.controller;

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
    @GetMapping("/test1")
    public String test1(){
        log.info("{}, access api:test/test1, input:{}", "1", "null");
        return "OK";
    }
}
