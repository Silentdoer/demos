package me.silentdoer.springbootimportimportresource.config.bean;

import org.springframework.context.annotation.Bean;

/**
 * 被@Import后确实可以也作为@Configuration的类存在
 *
 * @author liqi.wang
 * @version 1.0
 */
public class TestConfig {
    @Bean("foo2")
    public String foo2(){
        return new String("TTTTTT");
    }
}
