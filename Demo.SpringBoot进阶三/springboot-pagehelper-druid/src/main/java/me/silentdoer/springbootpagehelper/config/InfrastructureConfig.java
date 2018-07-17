package me.silentdoer.springbootpagehelper.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 2:39 PM
 */
@Configuration
@Slf4j
public class InfrastructureConfig {

    /**
     * 经过测试，@Value可以注解在@Bean的方法参数上
     */
    @Bean
    public ServletRegistrationBean<Servlet> statViewServlet(@Value("${custom.druid.login-username:test}") String username,
                                                            @Value("${custom.druid.login-password:test}") String password) {
        // 创建servlet注册实体
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");
        // 设置ip白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 设置ip黑名单，如果allow与deny共同存在时,deny优先于allow
        servletRegistrationBean.addInitParameter("deny", "192.168.0.19");
        // 设置控制台管理用户和密码，TODO 可以配置到配置文件里
        servletRegistrationBean.addInitParameter("loginUsername", username);
        servletRegistrationBean.addInitParameter("loginPassword", password);
        // 是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 这个filter是类似 Shiro 中的filter，然后这个filter又会自动加载一些不属于tomcat管理的filter，这里是如stat、wall、log4j之类的
     */
    @Bean
    public FilterRegistrationBean<Filter> statFilter() {
        // 创建过滤器
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>(new WebStatFilter());
        // 设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        // 要有否则会自动转换如#这些字符
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        gsonHttpMessageConverter.setGson(gson);
        messageConverters.add(gsonHttpMessageConverter);
        // 默认优先用的自定义的，因此优先用Gson，不需要自己去配置顺序
        return new HttpMessageConverters(true, messageConverters);
    }
}
