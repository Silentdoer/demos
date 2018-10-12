package me.silentdoer.demospringbootzookeeper.api.user.controller;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/17/2018 4:48 PM
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        InterProcessSemaphoreV2
    }
}
