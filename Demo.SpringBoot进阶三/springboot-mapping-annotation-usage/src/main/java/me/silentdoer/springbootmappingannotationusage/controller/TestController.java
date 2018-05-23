package me.silentdoer.springbootmappingannotationusage.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootmappingannotationusage.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {

    /**
     * 经过测试
     * @param student
     */
    @PostMapping("/test1")
    public ModelAndView test1(@ModelAttribute("stud") Student student, ModelAndView modelAndView){
        log.info(student.toString());
        modelAndView.setViewName("test");
        modelAndView.addObject("title", "你好标题")
                .addObject("content", "内容是双方家里飞888");
        return modelAndView;
    }
}
