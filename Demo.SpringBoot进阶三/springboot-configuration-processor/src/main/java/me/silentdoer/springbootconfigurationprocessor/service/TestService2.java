package me.silentdoer.springbootconfigurationprocessor.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@PropertySource(value = "classpath:test/test.properties")
@ConfigurationProperties(prefix = "mmm")
@Component
@Data
public class TestService2 {
    private String uu;

}
