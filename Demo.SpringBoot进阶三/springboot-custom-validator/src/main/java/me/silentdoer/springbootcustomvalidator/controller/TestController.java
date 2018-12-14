package me.silentdoer.springbootcustomvalidator.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcustomvalidator.model.MockModel;
import me.silentdoer.springbootcustomvalidator.model.MockModel2;
import me.silentdoer.springbootcustomvalidator.model.TestModel;
import me.silentdoer.springbootcustomvalidator.service.TestService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Validated
@RestController
@Slf4j
public class TestController {
    @Resource
    private TestService testService;

    /**
     * 普通的测试成功
     * @param foo
     * @return
     */
    @PostMapping("/test1")
    public String test1(@RequestBody @Valid TestModel foo){
        log.info("test1:{}", foo.toString());
        return "OK";
    }

    /**
     * 经过测试，在参数里用@Min无效，加上@Valid @Min也无效，目前只能用于Model的成员
     * @param id
     * @return
     */
    @GetMapping("/test2")
    public String test2(@RequestParam("id") @Min(value = 1, message = "太小") Long id){
        log.info("test2:{}", id);
        return "OK";
    }

    @GetMapping("/test3")
    public String test3(@RequestParam("pw") String pw){
        log.info("test3{}", pw);
        this.testService.doService(new TestModel().setPassword(pw));
        return "OK";
    }

    @GetMapping("/test4")
    public String test4(@ModelAttribute("foo") @Valid MockModel foo){
        log.info("test4,{}", foo.toString());
        return "OK";
    }

    @GetMapping("/test5")
    public String test4(@ModelAttribute("foo") @Valid MockModel2 foo){
        log.info("test4,{}", foo.toString());
        return "OK";
    }
}
