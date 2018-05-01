package me.silentdoer.springbootdemo01.controller;

import me.silentdoer.springbootdemo01.pojo.Student;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 12:31 PM
 */
@Controller("beanId")
@RequestMapping("/view")
public class MockController {

    // TODO freemaker生效，不过有时间可以查一下怎么配置freemaker的order
    @GetMapping("/test1")
    public String test1(Map<String, Object> model){
        List<Student> students = new ArrayList<>(1);
        Student stud = new Student();
        stud.setUid(100L);
        stud.setName("Silentdoer");
        stud.setGender("男");
        students.add(stud);
        stud = new Student();
        stud.setUid(111L);
        stud.setName("Dilentdoer");
        stud.setGender("女");
        students.add(stud);
        model.put("studs", students);
        model.put("message", "Hello, 哩哩啦啦");
        return "/test";
    }

    @GetMapping("/test2")
    public @ResponseBody String test2(){
        return "uuuu";
    }
}
