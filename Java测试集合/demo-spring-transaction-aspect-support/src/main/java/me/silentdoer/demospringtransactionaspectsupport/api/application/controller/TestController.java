package me.silentdoer.demospringtransactionaspectsupport.api.application.controller;

import me.silentdoer.demospringtransactionaspectsupport.api.application.model.po.Student;
import me.silentdoer.demospringtransactionaspectsupport.api.application.service.IStudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/27/2018 11:32 AM
 */
@RestController
@RequestMapping("/api/application/testSet")
public class TestController {

    @Resource
    private IStudentService studentService;

    @GetMapping("/account")
    public String account() {
        return "true" + this.studentService.insertSelective(Student.builder().fdGender(1).fdName("si").build());
    }
}
