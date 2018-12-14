package me.silentdoer.springbootmybatis.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootmybatis.dao.StudentMapper;
import me.silentdoer.springbootmybatis.dao.StudentNoAnnotationMapper;
import me.silentdoer.springbootmybatis.pojo.Student;
import me.silentdoer.springbootmybatis.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@RestController("mockController")
@RequestMapping("/student")
public class MockController {
    @Resource
    private StudentService studentService;
    /**
     * 这里是测试一下不通过注解实现Mybatis和SpringBoot的整合，因此就不写service了
     */
    @Resource
    private StudentNoAnnotationMapper studentNoAnnotationMapper;

    @GetMapping("/{uid}")
    public Student getStudent(@PathVariable("uid") Long uid){
        Student result;
        try {
            result = this.studentService.getSingleStudent(uid);
        }catch (Exception ex){
            log.debug(ex.toString());
            throw ex;
        }
        return result;
    }

    @GetMapping("/mapper/{uid}")
    public Student getStudent2(@PathVariable("uid") Long uid){
        Student student = this.studentNoAnnotationMapper.selectStudentByPrimaryKey(uid);
        log.info(student.toString());
        return student;
    }

    @GetMapping("/test1")
    public List<Student> getStudents(){
        return this.studentService.getStudents(1, 2);
    }
}
