package me.silentdoer.springboottest.service.impl;

import lombok.NonNull;
import me.silentdoer.springboottest.service.TestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author silentdoer
 * @version 1.0
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    private static int retryCount = 0;
    private static int recoverCount = 0;

    /**
     * 若产生了@Retryable标注的异常那么会尝试重新执行，如果重新执行仍然抛出异常那么会执行@Recover注解的方法
     * 这里maxAttempts的值是总共要尝试执行logicFunc多少次（包括第一次正常执行，默认是3），而delay则是每次尝试执行之间间隔多少毫秒
     * multiplier的值是1表示每次重试不叠加等待时长，如果此值为2,那么第一次重试需要5秒，第二次要10秒，第三次要15秒以此类推。
     */
    @Retryable(value= {RemoteAccessException.class},maxAttempts = 5, backoff = @Backoff(delay = 5000, multiplier = 1))
    @Override
    public void logicFunc() {
        // 执行了5次，每次之间间隔了5秒
        System.out.println("调用了retryable:" + (++TestServiceImpl.retryCount) + "次" + "，当前时间是：" + new Date());
        throw new RemoteAccessException("手动产生RemoteAccessException异常");
    }

    /**
     * 这个方法不应该由客户主动调用
     * @param ex 这个异常要能够承载@Retryable抛出的异常
     */
    @Recover
    @Override
    public void recover(@NonNull Exception ex) {
        // 5
        System.out.println("retryable未能重试成功，总共执行了:" + TestServiceImpl.retryCount + "次，异常信息为：" + ex.getMessage());
        // 1
        System.out.println(String.format("当前的recover执行了:%s次，当前时间是:%s", ++TestServiceImpl.recoverCount, new Date()));
    }

    private static int cacheCount = 0;

    /**
     * @Cacheable注解必须要有EnableCaching才能生效
     * @return
     */
    @Override
    @Cacheable("cache0")
    public String cacheReturnValue(){
        cacheCount ++;
        System.out.println("cacheReturnValue()方法执行了" + TestServiceImpl.cacheCount + "次");
        return "缓存了返回值";
    }
}
