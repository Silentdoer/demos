package me.silentdoer.springboottest.controller;

import me.silentdoer.springboottest.dao.StudentMapper;
import me.silentdoer.springboottest.pojo.Student;
import me.silentdoer.springboottest.service.AOPTestService;
import me.silentdoer.springboottest.service.PostOrderService;
import me.silentdoer.springboottest.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author silentdoer
 */
@RestController("mockController")
@RequestMapping(name="用于测试", path="/mock")
public class MockController {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TestService testService;
    @Resource
    private PostOrderService postOrderService;
    @Resource
    private AOPTestService aopTestService;

    @GetMapping(name="测试1", path="/test1")
    public String test1(){
        System.out.println("test1 is called.");
        return "啦啦啦";
    }

    @PostMapping("/test2")
    public String test2(Long uid){
        System.out.println("test2 is called.");
        return "UUU啦" + uid;
    }

    @RequestMapping(path="/test3/{uid}", method=RequestMethod.PUT)
    public String test3(@PathVariable("uid") Long uid){
        System.out.println("test3 is called.");
        return "中文" + uid;
    }

    /**
     * 用于测试和Mybatis的整合
     * @param uid Long
     * @return Student
     */
    @GetMapping("/test4/{uid}")
    public Student test4(@PathVariable("uid") Long uid){
        System.out.println("test4 is called." + uid);
        Student student = studentMapper.getSingleStudentByPrimaryKey(uid);
        System.out.println(student.getName());
        System.out.println(student);
        return student;
    }

    /**
     * 用于测试@Retryable和@Recover
     */
    @GetMapping("/test5")
    public void test5(){
        final TestService testService = this.testService;
        testService.logicFunc();
    }

    @PostMapping("/test6")
    public String test6(@RequestBody byte[] body){
        System.out.println(String.format("将这个方法认为是外界提交\"命令对象\"的接口，即可以立刻返回ok给对象，然后开启task来处理这些命令对象，body的长度为:%s", body.length));
        this.postOrderService.processNormalOrder(body);
        // TODO 这句话比上面的方法先执行完，说明@Async生效了
        System.out.println("调用task的方法的线程：" + Thread.currentThread().getName());
        return "OK";
    }

    /**
     * `@ResponseBody` 之后是通过HttpMessageConverter来转换，这里应该是通过json字符串来转换的，即将request的body的字节流转换为utf-8的字符串（json）然后再转换为对象
     * @param student Student
     * @return Student
     */
    @PostMapping("/test7")
    public Student test7(@RequestBody Student student){
        System.out.println(student);
        student.setName("特定的Name");
        return student;
    }

    /*
    @Cacheable可以标记在一个方法上，也可以标记在一个类上。当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的。
    对于一个支持缓存的方法，Spring会在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法。
     */

    private static int test8Count = 0;
    @GetMapping("/test8")
    public String test8(){
        test8Count ++;
        System.out.println(String.format("test8()方法执行了%s次", test8Count));
        return testService.cacheReturnValue();
    }

    @GetMapping("/test9")
    public void test9(){
        aopTestService.show();
    }

}
