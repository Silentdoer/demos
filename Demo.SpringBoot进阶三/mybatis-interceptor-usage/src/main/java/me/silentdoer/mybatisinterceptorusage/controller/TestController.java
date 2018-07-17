package me.silentdoer.mybatisinterceptorusage.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.mybatisinterceptorusage.model.StudentDo;
import me.silentdoer.mybatisinterceptorusage.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 4:27 PM
 */
@RestController
@RequestMapping("/pagehelper/druid")
@Slf4j
public class TestController {

    @Autowired
    private IStudentService studentService;

    @GetMapping("/test1/{name}")
    public List<StudentDo> test1(@PathVariable("name") String name) {
        List<StudentDo> studentByName = this.studentService.getStudentByName(name);
        return studentByName;
    }
}
