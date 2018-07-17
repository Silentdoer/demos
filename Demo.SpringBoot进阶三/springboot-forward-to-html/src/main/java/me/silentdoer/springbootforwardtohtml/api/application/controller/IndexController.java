package me.silentdoer.springbootforwardtohtml.api.application.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootforwardtohtml.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

/**
 * TODO 经过测试这个只能是Controller而不能是RestController，否则返回的/index就是字符串而不会进一步被ViewResolver处理；或者直接返回ModelAndView那么RestController也可以
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@RequestMapping(name = "首页")
public class IndexController {
    /**
     * TODO 注意，如果返回的index没有ViewResolver可以处理，那么它将作为forward，由SpringMVC进一步搜索mapping方法来处理，因此要正确配置ViewResolver否则会出现恒循环处理的情况
     *
     * ModelAndView 可以在参数上，也可以在内部自己new一个；
     */
    @GetMapping("/index")
    public ModelAndView index(ModelAndView mav) {
        // TODO 经过测试ModelAndView确实是在方法内部new就行了而不需要在参数上（但是最好在参数上加，毕竟可以省去new的步骤
        /*ModelAndView mav = new ModelAndView();*/
        log.info("index in");
        mav.setViewName("index");
        mav.addObject("title", "MMMMMMMMMss");
        mav.addObject("content", "内发生加快递费");
        User u = new User();
        u.setId(88L);
        u.setName("ui飞");
        mav.addObject("user", u);
        /*model.put("title", "AAAAAModel的标题");
        model.put("content", "内容是首页");
        // TODO 之前由于没有正确配置ViewResolver，导致返回的字符串无法被Freemarker的ViewResolver处理因而产生了循环跑进这个方法的情况
        // TODO 由此可以看出，配置path时确实应该配置最后的/，否则返回的字符串里带有/xx，有时候会和mapping的path一样有种会继续被mapping的感觉，而直接返回index感觉就是这是指向文件
        return "index";*/
        return mav;
    }

    /**
     * 配置直接访问如：http://localhost/时映射到index上
     * TODO 注意，"/"也是精确匹配，它不是URL-Pattern里的/，即http://localhost/mm如果找不到mapping方法也不会映射到这里来，mapping方法的path都是精确匹配，除非自己写表达式
     */
    /*@GetMapping("/")
    public String indexDefault(Map<String, Object> model){
        return index(model);
    }*/

    /**
     * TODO mapping方法里还是不要返回静态资源了，如果要那就直接当成普通文件读取后返回字符串
     * @return
     */
    @GetMapping("/static/main.html")
    @ResponseBody
    public String test2() {
        log.info("test2 in");
        Resource resource = new ClassPathResource("static/private/html/main.html");
        BufferedReader reader;
        StringBuilder sb = new StringBuilder(1024 * 1024 * 3);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getFile()), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        log.info("return before");
        return sb.toString();
    }
}
