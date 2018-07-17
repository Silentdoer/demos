package me.silentdoer.springbootnoweb;

import me.silentdoer.springbootnoweb.test.Student;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;

/**
 * TODO SpringBoot对于非 Web 应用需要实现 CommandLineRunner接口，然后run方法其实就可以理解为 main 方法，不同的是执行这个方法之前已经启动了spring，而结束这个方法后会调用各种
 * destroy之类的方法如DisposableBean的接口等等
 * TODO 通过实现 ApplicationContextAware 接口可以获取到，
 *
 * TODO 一定要消除一个 误解， main 方法属于类的静态方法这个误解一定要消除，  main 方法是独立方法， 可以理解为 C语言的 方法，它其实不属于任何类，只是 为了符合java规范而 勉强link到类
 */
@SpringBootApplication
public class SpringbootNoWebApplication implements CommandLineRunner, ApplicationContextAware {

    private AbstractApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNoWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // FLAG args 就是控制台参数列表（不包括路径和应用名，就是最后补充的那部分参数）
        Map<String, Student> beansOfType = this.applicationContext.getBeansOfType(Student.class);
        System.out.println((beansOfType));
        Object testServiceImpl = this.applicationContext.getBean("testServiceImpl");
        System.out.println(testServiceImpl.getClass());
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = ((AbstractApplicationContext) applicationContext);
    }
}
