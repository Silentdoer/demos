package me.silentdoer.springbootretry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@Service
public class TestService {
    /**
     * TODO 要配合@EnableRetry使用，也必须同时添加spring-boot-starter-aop依赖
     * value表示 doService方法抛出这几种异常将会根据maxAttempts等参数进行retry，原理也是用一个动态代理对此方法进行了try-catch这几个异常
     * TODO 由这里也可以看出 @Backoff其实就是类似 new Backoff{...}，因此注解既可以当注解用，也可以当成员或参数用
     * maxAttempts表示最大尝试的次数默认是3，而backoff中delay值表示 第一次抛出 了对应的异常过多久去retry，而multiplier的值则表示当前retry的delay时间是上次的多少倍
     * TODO maxAttempts = 3，表示包括第一次正常执行总要尝试3次；
     * @return
     */
    @Retryable(value = {IllegalArgumentException.class, IllegalStateException.class}, maxAttempts = 2, backoff = @Backoff(delay = 2000L, multiplier = 2))
    public void doService(Long id){
        // TODO 由于上面的delay是3000而multiplier是1，那么这里是每隔3秒执行一次，如果multiplier是2，那么第一次是隔3秒，第二次是隔6秒，第三次是隔12秒（不是9秒）
        log.info("doService called." + new Date());
        if(true) {
            throw new IllegalArgumentException("不合法的参数");
        }
        //return id;
    }

    /**
     * 这个方法要为public，但是不应该主动去调用
     * 如果Recover没有ex，那么这个recover方法是没有作用的（TODO 注意，这个ex可以设置为所以异常的基类Exception，因此只需要一个@Recover即可，而且Recover接口没有成员）
     *
     * TODO 重要，如果@Retryable的方法有返回值，则在所有的attempt后它将会抛异常给调用doService的方法（这里是TestController里），@Recover不起作用
     * TODO 但是如果@Retryable是void，那么@Recover是会起作用的；
     * @return
     */
    @Recover
    public void recover(@NonNull Exception ex){
        log.info("recover called." + new Date() + ex.getMessage());
    }
}
