package me.silentdoer.springbootservletinputstreamtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {
    @PostMapping("/test1")
    public String test1(HttpServletRequest request) throws IOException {
        log.info("stream size:{}", request.getInputStream().available());
        return "OK";
    }
}
