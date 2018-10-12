package silentdoer.web.controller;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import silentdoer.web.dao.StudentMapper;
import silentdoer.web.entity.Student;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
//@RequestMapping("/hello")  // 在Controller上加"/hello"和在方法中前面加一个"/hello/aaa"是一样的，这里只是为了省事，方法里return时是什么就是什么不会在前面自动加上"/hello"
public class HelloController {

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/aaa")
    public String hello(Model model){
        model.addAttribute("msg", "叽哩叽哩~呱啦呱啦");
        return "hello/hello";
    }

    @RequestMapping(value = "/bbb", method = RequestMethod.POST)
    public String hello2(Model model){
        model.addAttribute("msg", "咳咳咳看看");
        return "hello/hello";
    }

    // 当浏览器中输入的是aaa.html时，实际上也默认设置了http头信息的accept
    // 注意，这种情况下就不能在地址栏后面输入aaa.html或aaa.htm，因为这里ResponseBody规定了返回的是普通字符串（Json也可以设置类型是普通字符串，不过要配置转换器）
    // 注意params的这种形式也是可以的，不是说一定要 aa=bb 的形式，这里访问时是http:...../aaa.html?getStr能够访问成功，还可以是"!getStr"表示不是getStr即可等等很多种方式
    @RequestMapping(value = "/aaa", params = "getStr")
    public @ResponseBody String getStr(){
        return "ssd中文??“";
    }

    // 这个则可以访问，返回值也正确。
    @RequestMapping("/ssmm/bbc")
    public @ResponseBody String getStr3(){
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.err.println("1-start" + i);
                try {

                    /*int limit = 500;
                    int page = 1;
                    PageBounds pageBounds = new PageBounds(page++, limit);
                    List<IdDatetimeModel> records = userMapper.selectAllNewUserWithRegisterTime(pageBounds);*/

                    int limit = 500;
                    int start = 0;
                    RowBounds rowBounds = new RowBounds(start++, limit);
                    List<Student> records = studentMapper.forTest3(rowBounds);

                    /*int limit = 500;
                    int page = 1;
                    int start = (page - 1) * limit;
                    page++;
                    List<IdDatetimeModel> records = userMapper.forTest(start, limit);*/

                    /*List<IdDatetimeModel> records = userMapper.forTest2();*/

                    System.err.println("1-第" + i + "的获得的记录数是:" + records.size());
                } catch (Exception e) {
                    System.err.println(e.toString() + "######");
                    e.printStackTrace();
                }
                System.err.println("1-end" + i);
            }
            System.err.println("111111结束");
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.err.println("2-start" + i);
                try {

                    /*int limit = 500;
                    int page = 1;
                    PageBounds pageBounds = new PageBounds(page++, limit);
                    List<IdDatetimeModel> records = userMapper.selectAllNewUserWithRegisterTime(pageBounds);*/

                    int limit = 500;
                    int start = 0;
                    RowBounds rowBounds = new RowBounds(start++, limit);
                    List<Student> records = studentMapper.forTest3(rowBounds);

                    /*int limit = 500;
                    int page = 1;
                    int start = (page - 1) * limit;
                    page++;
                    List<IdDatetimeModel> records = userMapper.forTest(start, limit);*/

                    /*List<IdDatetimeModel> records = userMapper.forTest2();*/

                    System.err.println("2-第" + i + "的获得的记录数是:" + records.size());
                } catch (Exception e) {
                    System.err.println(e.toString() + "$$$$$$$$");
                    e.printStackTrace();
                }
                System.err.println("2-end" + i);
            }
            System.err.println("22222结束");
        }).start();
        return "DKFJKD";
    }

    // 一样不能访问，tomcat等服务器是禁止直接访问WEB-INF目录的
    @RequestMapping("/WEB-INF/view/student")
    public @ResponseBody String getStr2(){
        return "Hello3333";
    }

    @RequestMapping("/testAA")
    public @ResponseBody Student getStudent() {
        return this.studentMapper.selectByPrimaryKey(1L);
    }
}
