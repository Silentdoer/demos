package me.silentdoer.springbootaop.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootaop.component.ApiBefore;
import me.silentdoer.springbootaop.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping(name = "测试")
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/test1")
    public String test1(Long id){
        log.info("test1");
        return "OK";
    }

    @ApiBefore
    @GetMapping("/test2")
    public void test2(){
        log.info("test2");
    }

    @PutMapping("/test3")
    public void test3(){
        log.info("test3");
        this.testService.service1();
    }

    @GetMapping("/test4")
    public Long test4(){
        log.info("test4");
        return this.testService.service2(1, "UUU");
    }

    @GetMapping("/test5")
    public void test5(){
        log.info("test5");
        this.testService.service3(233);
    }

    @GetMapping("/test6")
    public void test6(){
        log.info("test6");
        this.testService.service4(33, "MM");
    }

    @DeleteMapping("/test7")
    public void test7(){
        log.info("test7");
    }
}
