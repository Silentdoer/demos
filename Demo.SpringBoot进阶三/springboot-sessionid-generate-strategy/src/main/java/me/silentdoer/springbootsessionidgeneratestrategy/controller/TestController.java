package me.silentdoer.springbootsessionidgeneratestrategy.controller;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/19/2018 6:18 PM
 */
@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @GetMapping("/test1")
    public String test1(HttpSession session) {
        log.info("test1 in,{},{}", session.getClass(), session.getId());
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        // 1:e0076752-b896-4109-bff2-34df90398221；2:add7793c-17d0-4fca-bfcf-93d02d2ef2eb；3:6e4428c7-64ff-43ae-956d-df5567822d16
        log.info("{}", sessionId);
        return sessionId;
    }
}
