package me.silentdoer.springbootprofileannotation.config;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootprofileannotation.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Configuration
public class TestConfig {

    @Profile("dev")
    @Bean
    public Student studentAAA() {
        return new Student();
    }
}
