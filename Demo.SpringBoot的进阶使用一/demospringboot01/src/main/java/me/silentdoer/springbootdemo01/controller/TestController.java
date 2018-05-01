package me.silentdoer.springbootdemo01.controller;

import me.silentdoer.springbootdemo01.mapper.MockMapper;
import me.silentdoer.springbootdemo01.pojo.Student;
import me.silentdoer.springbootdemo01.test.SimpleClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 11:05 AM
 */
@Controller("beanName")  // TODO 如果换成RestController则可以理解为内部所有@RequestMapping的方法都有@ResponseBody注解
@ResponseBody  // 没有任何成员，包括value，可以和Controller合成一个RestController
@RequestMapping(name="此controller的作用")
public class TestController implements BeanFactoryAware {  // Mock和Test和Foo有类似的效果，前两者一般用于类名表示是测试，而foo则是常用的变量名
    private BeanFactory beanFactory;

    @Autowired
    private MockMapper mockMapper;

    @RequestMapping(path="/test", method = RequestMethod.GET)
    public String test(Long uid){
        String singleName = mockMapper.getSingleName(uid);
        return uid + 88888 +"" + singleName;
    }

    @GetMapping("/test2")  // 就类似RequestMapping且method=RequestMethod.GET，但是注意没有@ResponseBody
    public String test2(Long uid){
        DefaultListableBeanFactory beanFactory = ((DefaultListableBeanFactory) this.beanFactory);
        System.out.println(beanFactory.getBeanNamesForType(SimpleClass.class).length);
        return uid + 99999 + "";
    }

    // TODO 能自动将对象转换为JSON字符串，其实还是因为存在HttpMessageConverter（默认的是jackson）
    @GetMapping("/test3")
    public Student test3(){
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("myLogger");
        logger.error("测试logger是否有用");  // 生效了
        Student s = new Student();
        s.setUid(88L);
        s.setName("silentdoer");
        s.setGender("male");
        return s;
    }

    @PostMapping("/test4")
    public Student test4(@RequestBody byte[] body){
        System.out.println(body.length);
        return new Student();
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
