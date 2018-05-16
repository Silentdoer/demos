package me.silentdoer.springbootsession.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test1")
    public String test1(HttpSession httpSession){
        // TODO 注意，此时用的仍然是apache的session，且会自动给response写Set-Cookie
        log.info(httpSession.getClass().getName());
        log.info("{},test", "HHHHHHHHHHHHHH");
        return httpSession.getId();
    }
}
