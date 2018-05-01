package me.silentdoer.springbootdemo01;

import me.silentdoer.springbootdemo01.config.TestBeansConfiguration;
import me.silentdoer.springbootdemo01.test.SimpleClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 8:36 AM
 */

/** @SpringBootApplication里有：
 @SpringBootConfiguration
 @EnableAutoConfiguration
 @ComponentScan
 */
@SpringBootApplication
//TODO 这个类最好就在app的包的根路径下，因为默认不指定basePackages（即会对此包下所有的类进行component-scan，符合filter的就添加到IoC容器里）
public class MainApplication{
    // TODO 注意，resources中的static/public/templates均代表webapp根目录，因此除了如index.html页面都不应该放在static的一级目录
    // TODO 且，如果static/html/test.html文件存在，而又有RequestMapping("/html/test")那么context/html/test.html优先被controller捕获

    public static void main(String[] args){
        // 第一个参数是将clazz作为spring的配置参数（如@Configuration类，@ComponentScan类）
        SpringApplication.run(new Class[] {MainApplication.class, TestBeansConfiguration.class}, args);
    }

    /*@Bean
    public SimpleClass simpleClass1(){  // 有效，确实会生成一个bean
        return new SimpleClass();
    }*/
}
