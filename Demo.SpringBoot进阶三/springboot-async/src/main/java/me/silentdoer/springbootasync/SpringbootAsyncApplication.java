package me.silentdoer.springbootasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Base64Utils;

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
