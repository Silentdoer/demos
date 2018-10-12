package me.silentdoer.demosimpleproj.api.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.silentdoer.demosimpleproj.core.component.AppAuthHandlerChain;
import me.silentdoer.demosimpleproj.core.component.InputStreamReuseFilter;
import me.silentdoer.demosimpleproj.core.component.StringRedisTemplateFacade;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/29/2018 7:10 PM
 */
@Configuration
public class InfrastructureConfig {

    @Bean
    public FilterRegistrationBean<Filter> reuseInputStreamFilterBean() {
        FilterRegistrationBean<Filter> result = new FilterRegistrationBean<>();
        result.setFilter(new InputStreamReuseFilter());
        // 设置为最高优先级，否则一些默认的Filter可能会预先读取了request.inputStream
        result.setOrder(Integer.MIN_VALUE);
        result.setName("reuseInputStreamFilter");
        result.addUrlPatterns("/*");
        return result;
    }

    @Bean
    public Gson generalGson() {
        return new GsonBuilder().disableHtmlEscaping().create();
    }

    @Bean
    public StringRedisTemplateFacade stringRedisTemplateFacade() {
        // 这个对象会交由Spring管理，因此它内部的如@Value或@Autowired等都会生效
        return new StringRedisTemplateFacade();
    }

    @Bean
    public AppAuthHandlerChain appAuthHandlerChain() {
        return new AppAuthHandlerChain();
    }
}
