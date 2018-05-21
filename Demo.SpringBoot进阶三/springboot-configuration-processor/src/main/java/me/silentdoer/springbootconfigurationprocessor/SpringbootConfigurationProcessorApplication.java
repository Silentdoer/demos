package me.silentdoer.springbootconfigurationprocessor;

import me.silentdoer.springbootconfigurationprocessor.controller.TestController;
import me.silentdoer.springbootconfigurationprocessor.service.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class SpringbootConfigurationProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootConfigurationProcessorApplication.class, args);
    }
}
