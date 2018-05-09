package me.silentdoer.springbootimportimportresource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * `@Import`就像xml配置里的import一样，注意TestConfig上不需要@Configuration注解
 *
 * @author liqi.wang
 * @version 1.0
 */
@Configuration
@Import(me.silentdoer.springbootimportimportresource.config.bean.TestConfig.class)
public class ApplicationContextConfig {
    @Scope("prototype")
    @Bean("foo")
    public String foo(){
        return new String("UUUUUU");
    }
}
