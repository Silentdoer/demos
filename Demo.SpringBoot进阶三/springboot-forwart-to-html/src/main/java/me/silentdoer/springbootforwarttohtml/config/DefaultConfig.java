package me.silentdoer.springbootforwarttohtml.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Configuration
public class DefaultConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加类似<mvc:resource
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问http://localhost:8080/static2/html/main.html成功映射到/static/private/html/main.html上
        registry.addResourceHandler("/static2/**").addResourceLocations("classpath:/static/private/");
        super.addResourceHandlers(registry);
    }
}