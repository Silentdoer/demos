package me.silentdoer.springbootmybatis;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 用于生成war包并发布到tomcat
 * @author silentdoer
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /*
         * 可通过数组的形式弄多个source
         */
        return application.sources(SpringbootMybatisApplication.class);
    }

}
