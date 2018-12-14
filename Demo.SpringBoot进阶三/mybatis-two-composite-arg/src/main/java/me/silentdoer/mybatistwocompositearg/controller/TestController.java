package me.silentdoer.mybatistwocompositearg.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.mybatistwocompositearg.dao.StudentMapper;
import me.silentdoer.mybatistwocompositearg.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {
    @Resource
    private StudentMapper studentMapper;

    @GetMapping("/test1")
    public String test1() {
        Student student = new Student();
        student.setName("sjfd在");
        student.setGender('M');
        this.studentMapper.insertStudent(student, 8L);
        return student.toString();
    }
}
