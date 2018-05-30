package me.silentdoer.springbootrereadableinputstream.config;

import me.silentdoer.springbootrereadableinputstream.filter.InputStreamReuseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Configuration
public class InfrastructureConfig {

    @Bean
    public FilterRegistrationBean<Filter> reuseInputStreamFilterBean(){
        FilterRegistrationBean<Filter> result = new FilterRegistrationBean<>();
        result.setFilter(new InputStreamReuseFilter());
        result.setOrder(Integer.MIN_VALUE);
        result.setName("reuseInputStreamFilter");
        result.addUrlPatterns("/*");
        return result;
    }
}
