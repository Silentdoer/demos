package me.silentdoer.springbootpropertiesvalue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * TODO 经过测试@Configuration和@Component有同样的效果会将TestConfig最为一个bean，且是singleton，而且beanName就是testConfig
 * TODO 而且Configuration注解里有@Component注解，@Repository和@Service均有该注解
 * @author liqi.wang
 * @version 1.0.0
 */
@Configuration
public class TestConfig {
}
