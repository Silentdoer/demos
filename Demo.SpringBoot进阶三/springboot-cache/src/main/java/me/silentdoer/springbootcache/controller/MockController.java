package me.silentdoer.springbootcache.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcache.model.Student;
import me.silentdoer.springbootcache.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当Controller里有多种RequestMethod时就用RequestMapping而不要用GetMapping之类的
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@RequestMapping("/mock")
@Slf4j
public class MockController {
    @Autowired
    private MockService mockService;

    @GetMapping("/test1")
    public Student test1(Long arg){
        log.info("test1");
        return this.mockService.doService(arg, "ui");
    }
}
