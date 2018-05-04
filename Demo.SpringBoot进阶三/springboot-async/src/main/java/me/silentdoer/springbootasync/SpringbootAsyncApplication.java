package me.silentdoer.springbootasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author silentdoer
 */
@SpringBootApplication
//@EnableAsync
public class SpringbootAsyncApplication {

    public static void main(String[] args) {
        // 可以不用new Class[]{SpringbootAsyncApplication.class, ExecutorConfiguration.class}
        SpringApplication.run(new Class[]{SpringbootAsyncApplication.class}, args);
    }
}
