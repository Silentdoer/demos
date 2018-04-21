package silentdoer.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import silentdoer.web.entity.Student;
import silentdoer.web.service.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/student")
@Controller
public class StudentController {

    Logger logger = Logger.getLogger(StudentController.class);

    @Resource
    private StudentService studentService;

    @RequestMapping("/show")
    public String show(){
        return "student";
    }

    /*
    // 如果说没有HttpServletResponse且Writer赋值，那么即便返回类型是void，viewResolver也会返回一个视图，该名字和RequestMapping中的一样，即student/add（这种情况不仅仅是add）
    // 返回值是void的尽量不要写，之前是因为需要返回普通字符串会用到，后面有了Converter后可以直接在ResponseBody的注解下返回中文字符串，故void的类型已经不再需要。
    @RequestMapping("/add")
    public void addSelective(String name, String sex){
        // 插入产生了中文乱码，检查一下 bug，可能是web.xml里面少了编码过滤器，加上试试（貌似不是过滤器的问题，但是也要加上，这次是IDE的问题，即本项目的编码不是UTF8的，改过来就好了）
        logger.info("add ok");  // 可输出到控制台
        //System.out.println("add sssss ok");
        Student std = new Student();
        std.setStdName(name);
        std.setStdSex(sex);
        studentService.addSelective(std);
        //return "student";  // return "redirect:/index.jsp";
    }
    */

    // 注意，参数最好都是引用类型的参数，比如你的是int那就用Integer代替
    // 注意这里用了实体对象，则前端的参数的name值要和Student的属性名对应（相当于Student中的属性名就是这里声明的参数名，那用RequestParam进行转换也可以吗？？）
    @RequestMapping("/add")
    public String addSelective(Student std){
        //httpServletRequest.getHeader("accept");  // 这里面的值貌似不区分大小写的，即Accept也可以，但一般都是全小写
        logger.info("std add ok");
        System.out.println(std);
        studentService.addSelective(std);
        return "student";
    }
}
