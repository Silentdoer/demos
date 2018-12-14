package me.silentdoer.demolocaldevprodconfigload.api.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/13/2018 4:45 PM
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Value("${aaa}")
    private String aaa;

    @Value("${bbb}")
    private String bbb;

    @GetMapping("/test1")
    public String test1() {
        System.out.println(this.aaa + this.bbb);
        return this.aaa + this.bbb;
    }
}
