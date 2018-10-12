package me.silentdoer.demosimpleproj.core.config;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.api.common.component.AppAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/12/2018 4:32 PM
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // argumentResolvers.add(new JsonMapperArgumentResolver());
    }

    /**
     * 经过测试，这种写法可以，尽管addInterceptors不是@Bean，但是@Bean可以看成是@Autowired，因此appAuthInterceptor()其实就是一个成员变量，自然是可以的
     * 即Spring优先产生各类bean，之后才是执行相关bean的组件配对；
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 如果某个bean不需要由IOC容器管理，那么直接new就行了；
        registry.addInterceptor(appAuthInterceptor());
    }

    @Bean
    public  AppAuthInterceptor appAuthInterceptor() {
        return new AppAuthInterceptor();
    }
}
