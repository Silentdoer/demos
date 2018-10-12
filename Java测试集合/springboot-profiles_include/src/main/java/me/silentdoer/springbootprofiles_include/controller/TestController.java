package me.silentdoer.springbootprofiles_include.controller;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/27/2018 11:35 AM
 */
@RestController
public class TestController {

    @Value("${foo}")
    private String uuu;

    @Value("${uuu}")
    private String shit;

    @Value("${ttt}")
    private String ly;

    @GetMapping("/test")
    public String test() {
        return "AAAA" + uuu + shit + ly;
    }
}
