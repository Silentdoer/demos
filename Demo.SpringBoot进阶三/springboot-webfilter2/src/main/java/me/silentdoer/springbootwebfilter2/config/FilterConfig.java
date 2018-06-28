package me.silentdoer.springbootwebfilter2.config;

import me.silentdoer.springbootwebfilter2.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.Filter;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Configuration
public class FilterConfig {

    /*
    <filter>
        <filter-name>ffff</filter-name>
        <filter-class>xxx.MyFilter</filter-class>
        <init-param>
            <param-name></param-name>
            <param-value></param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>ffff</filter-name>
        <url-pattern>/*</url-pattern>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
     */

    /**
     * 这个实际上是对应一个<filter></filter>
     *
     * TODO 经过测试，是默认存在的hiddenHttpMethodFilter导致执行了request.getParameter("_method")方法使得流被读取了；
     * 注意默认存在的filter已经成为了bean由Spring管理
     */
    @Bean
    public FilterRegistrationBean<Filter> firstSeqWebFilter(){
        FilterRegistrationBean<Filter> result = new FilterRegistrationBean<>();
        // 对应<filter-name>
        result.setName("defaultFilter");
        // 类似对应<filter-class>
        result.setFilter(defaultFilter());
        // 对应一个init-param
        result.addInitParameter("defaultFilterKey", "defaultFilterValue");
        // can like "/..", "/..."，对应多个<url-pattern>
        result.addUrlPatterns("/*");
        // TODO 如果没有这一句的话这个filter的优先级是不够高的，这个servletRequest的输入流已经被读取掉了；（SpringBoot里存在默认的Filter）
        // TODO 而且经过测试@Order在这个bean上或下面的bean上都没用
//        result.setOrder(Integer.MIN_VALUE);
        return result;
    }

    @Bean("defaultFilter")
    public Filter defaultFilter(){
        TestFilter result = new TestFilter();
        return result;
    }
}
