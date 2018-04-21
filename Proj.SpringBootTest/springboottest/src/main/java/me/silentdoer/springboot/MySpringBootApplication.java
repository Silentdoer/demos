package me.silentdoer.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/12/18 7:17 PM
 */

/**
 * TODO 此注解指定下面的类是springboot的启动类，然后SpringApplication.run(...)时会自动去加载依赖的jar包/模块等（如tomcat）
 * 顺便还从这个注解的源码里学到了创建注解对象时还可以通过“构造方法”初始化“属性”，然后注解的声明里还可以声明内部注解
 */
@SpringBootApplication
public class MySpringBootApplication {
    public static void main(String[] args){
        // TODO 注意，tomcat本质上是一个java application，这里内部实际上是运行了一个tomcat
        // TODO 实现原理可以看：org.apache.catalina.startup.Bootstrap.main()方法
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}
