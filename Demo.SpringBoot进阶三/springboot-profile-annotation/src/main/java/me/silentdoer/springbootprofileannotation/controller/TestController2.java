package me.silentdoer.springbootprofileannotation.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping
public class TestController2 {

    @Profile("dev")
    @GetMapping("/test21")
    public String test21() {
        return "UUU";
    }

    @Profile("prod")
    @GetMapping("/test22")
    public String test22() {
        return "M<M";
    }
}
