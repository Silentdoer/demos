package me.silentdoer.springbootservletinputstreamtest.config;

import me.silentdoer.springbootservletinputstreamtest.filter.DefaultFilter;
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

    public FilterRegistrationBean<Filter> defaultFilter() {
        FilterRegistrationBean<Filter> result = new FilterRegistrationBean<>();
        Filter filter = new DefaultFilter();
        result.setFilter(filter);
        result.addUrlPatterns("/*");
        result.setName("defaultFilter");
        result.setOrder(0);
        return result;
    }
}
