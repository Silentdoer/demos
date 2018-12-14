package me.silentdoer.demomysqlcharsettest.test.controller;

import me.silentdoer.demomysqlcharsettest.test.dao.StudentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/6/2018 8:30 PM
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private StudentMapper studentMapper;

    @GetMapping("/test1")
    public String test1() {
        this.studentMapper.insert();
        return "UUU";
    }
}
