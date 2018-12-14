package me.silentdoer.springbootheaderignorecasetest.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@RestController
public class TestController {
    @GetMapping("/test1")
    public String test1(@RequestHeader("Raw-Data-Token") String rawDataToken) {
        log.info("{}", rawDataToken);
        return rawDataToken;
    }
}
