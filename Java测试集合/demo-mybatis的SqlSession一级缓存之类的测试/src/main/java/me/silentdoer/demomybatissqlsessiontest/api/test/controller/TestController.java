package me.silentdoer.demomybatissqlsessiontest.api.test.controller;

import me.silentdoer.demomybatissqlsessiontest.api.test.dao.StudentMapper;
import me.silentdoer.demomybatissqlsessiontest.api.test.model.po.Student;
import me.silentdoer.demomybatissqlsessiontest.api.test.service.impl.TestService1Impl;
import me.silentdoer.demomybatissqlsessiontest.api.test.service.impl.TestService2Impl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/3/2018 5:44 PM
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TestService1Impl testService1;

    @Resource
    private TestService2Impl testService2;

    @GetMapping("/test1")
    public String test1() {
        Student student = this.studentMapper.selectById(11L);
        this.studentMapper.selectById(11L);
        return "UUUTest1" + student.getFdName();
    }

    @GetMapping("/test2")
    public String test2() {
        return this.testService1.test11();
    }

    @GetMapping("/test3")
    public String test3() {
        return this.testService2.test21();
    }
}
