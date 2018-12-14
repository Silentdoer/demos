package me.silentdoer.springbootbeannamegeneratorstrategy;

import me.silentdoer.springbootbeannamegeneratorstrategy.support.generator.MyAnnotationBeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * TODO 重要：SpringBoot里如果要自定义BeanNameGenerator，那么需要用下面的三个注解替换 @SpringBootApplication
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) },
               // TODO 重要，添加自己的BeanNameGenerator来替换默认的
               nameGenerator = MyAnnotationBeanNameGenerator.class)
public class SpringbootBeannameGeneratorStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBeannameGeneratorStrategyApplication.class, args);
    }
}
