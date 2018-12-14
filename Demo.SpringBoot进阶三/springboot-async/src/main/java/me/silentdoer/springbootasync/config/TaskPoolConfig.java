package me.silentdoer.springbootasync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author silentdoer
 * @version 1.0
 */
@EnableAsync
@Configuration
public class TaskPoolConfig {

    /**
     * ThreadPoolExecutor实现了ServiceExecutor，而ServiceExecutor接口又扩展自Executor
     * @return 返回自定义的Executor（线程池）
     */
    @Bean("myExecutor")
    public AsyncTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(1024);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("myExecutor-");
        //
        taskExecutor.setRejectedExecutionHandler((r, e) -> System.err.println(String.format("Runnable对象：%s被Executor：%s拒绝", r, e)));
        return taskExecutor;
    }
}
