package silentdoer.web.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;
import silentdoer.web.entity.Student;
import silentdoer.web.service.StudentService;

import javax.annotation.Resource;

/**
 * 据说Log4j快要被Slf4j取代了
 */

@RunWith(SpringJUnit4ClassRunner.class)  // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:mybatis-spring.xml"})
public class StudentServiceImplTest {

    private static Logger logger = Logger.getLogger(StudentServiceImplTest.class);
    // 这里之所以可以不new BookService就能用bookService是因为这里加了@Resource注解吗？然后MyBatis会自动为bookService生成对象？（或叫注入什么的？）
    @Resource
    private StudentService studentService;

    // JUnit4不能测试aop，故还是需要一个main方法进行一些测试。
    @Test
    public void test() {
        try {
            // 注意，默认情况下Log4j是从classes子一级目录下找log4j.properties文件，如果该文件放在其它位置则需要用下面的代码手动指定
            Log4jConfigurer.initLogging("classpath:properties/log4j.properties");
        }catch (Exception ex){
            // Ignored
        }
        Student std = studentService.getByPrimaryKey(1L);
        System.out.println("#--------------------------------------------------------------------------------------------------------------------------------------#");
        logger.info(JSON.toJSONString(std));
        System.out.println("#--------------------------------------------------------------------------------------------------------------------------------------#");
        System.out.println(JSON.toJSONString(std));
        System.out.println("#--------------------------------------------------------------------------------------------------------------------------------------#");
    }
}
