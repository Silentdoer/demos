package me.silentdoer.springbootrereadableinputstream.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {
    @PostMapping("/test1")
    public String test1(@RequestBody byte[] body){
        log.info("{}", new String(body, StandardCharsets.UTF_8));
        return "OK";
    }

    @PostMapping("/test2")
    public String test2(HttpServletRequest request) throws IOException {
        log.info("{},{},{}", request.getContentType(), request.getContentLength(), request.getMethod());
        log.info("{}, {}", request.getCharacterEncoding(), request.getInputStream().available());
        log.info("{}", new String(IOUtils.toByteArray(request.getInputStream()), StandardCharsets.UTF_8));
        return "OK";
    }

    @PostMapping("/test3")
    public String test3(InputStream inputStream) throws IOException {
        log.info("{}, {}", inputStream.available());
        return "O222K";
    }
}
