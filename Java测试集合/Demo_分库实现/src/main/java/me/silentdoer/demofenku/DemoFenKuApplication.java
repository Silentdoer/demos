package me.silentdoer.demofenku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
public class DemoFenKuApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoFenKuApplication.class, args);
    }
}
