package me.silentdoer.springbootdemo01.config;

import me.silentdoer.springbootdemo01.test.SimpleClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 12:25 PM
 */
@Configuration
//@ImportResource("classpath:config/spring/test/beans.xml")  // 有效
public class TestBeansConfiguration {

    /*@Bean
    public SimpleClass simpleClass1(){  // 有效，确实会生成一个bean
        return new SimpleClass();
    }*/

    @Bean
    @Profile("produce")  // 只对生产环境生效
    public SimpleClass simpleClass1(){  // 有效，确实会生成一个bean
        return new SimpleClass();
    }

    /*@Bean  // 就不要使用jsp
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/content/jsp");
        viewResolver.setSuffix(".jsp");
        // viewResolver.setViewClass(JstlView.class); // 这个属性通常并不需要手动配置，高版本的Spring会自动检测
        return viewResolver;
    }*/
}
