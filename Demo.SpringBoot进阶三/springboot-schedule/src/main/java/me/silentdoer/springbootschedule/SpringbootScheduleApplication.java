package me.silentdoer.springbootschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableScheduling
@SpringBootApplication
public class SpringbootScheduleApplication {

    @Bean
    public TaskScheduler taskScheduler() {
        /**
         * TODO 注意有个ThreadPoolTaskExecutor和这个名字很像，但它是用在给@Async配置线程池；而且那个一般命名为taskExecutor，这个是taskScheduler
         *
         * TODO 他们在xml里的配置也不一样，一个是<task:executor，一个是<task:scheduler
         *
         * 这个可以在xml里配置但不能在application.properties里配置
         *
         * 可以看到配置了这个后schedule会在不同的线程里执行；（TODO 默认是串行的执行，现在是并行执行）
         */
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //线程池大小
        scheduler.setPoolSize(10);
        //线程名字前缀
        scheduler.setThreadNamePrefix("spring-task-thread");
        return scheduler;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootScheduleApplication.class, args);
    }
}
