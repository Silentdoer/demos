package me.silentdoer.springbootatvaluespacetest.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/5/2018 11:19 AM
 */
@RestController
@RequestMapping(name = "sfd")
@Slf4j
public class TestController {

    @Value("${custom.url.test-mm}")
    private String test;

    @GetMapping("/test")
    public String test() {
        log.info("##{}##", this.test);
        return "MMMM";
    }
}
