package me.silentdoer.springbootnoweb.config;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootnoweb.test.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 11:21 AM
 */
@Configuration
public class InfrastructureConfig {

    @Bean
    @Scope(value = "singleton")
    public Student firstStudent() {
        return new Student();
    }
}
