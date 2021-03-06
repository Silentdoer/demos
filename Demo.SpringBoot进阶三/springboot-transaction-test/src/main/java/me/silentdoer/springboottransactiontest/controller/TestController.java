package me.silentdoer.springboottransactiontest.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springboottransactiontest.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/9/2018 6:30 PM
 */
@RestController
@Controller
@Slf4j
public class TestController {

    @Autowired(required = true)
    private ITestService testService;

    @GetMapping("/test")
    public Boolean test(@RequestParam("name") String name, @RequestParam("gender") Character gender,
                        @RequestParam(value = "rollback", required = false, defaultValue = "false") Boolean rollback) {
        log.info("{}, {}, {}", name, gender, rollback);
        int i = -1;
        try {
            i = this.testService.addStudent(name, gender, rollback);
        } catch (Exception e) {
            log.error("###, {}", e.toString());
        }
        log.info("{}", i);
        return i == 1;
    }
}
