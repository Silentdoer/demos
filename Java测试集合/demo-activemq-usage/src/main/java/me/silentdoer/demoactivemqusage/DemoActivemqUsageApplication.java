package me.silentdoer.demoactivemqusage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DemoActivemqUsageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoActivemqUsageApplication.class, args);
    }
}
