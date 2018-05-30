package me.silentdoer.springboottest.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author silentdoer
 * @version 1.0
 */
@Component
public class PrintTimer {

    /**
     * 当同一个方法上有多个@Scheduled注解时，可以用@Schedules来替换，@Scheduled可以理解为new Scheduled{...}
     */
    /*@Schedules({@Scheduled(fixedDelay = 100*5), @Scheduled(cron = "* * * * * *")})*/
    @Scheduled(fixedDelay = 1000*5)  // 毫秒为单位，和fixedRate的区别在于这个是要等上一次执行完毕后才等5秒执行下一个，而fixedRate是不等的
    /**
     * TODO 有cron/fixedRate/fixedDelay
     */
    public void print(){
        System.err.println("Schedule触发了一次" + new Date());
    }
}
