package me.silentdoer.springbootconfigurationprocessor.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "silentdoer")
@Service
public class TestService {
    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
