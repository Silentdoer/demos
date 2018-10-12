package me.silentdoer.demosimpleproj.core.component;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/9/2018 5:53 PM
 */
public class StringRedisTemplateFacade {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 不用:来设置默认值，如果不给出此值则直接抛出异常
     */
    @Value("${custom.redis.default-expire-time}")
    private long timeout;

    public Long incr(String key) {
        return this.stringRedisTemplate.opsForValue().increment(key, 1L);
    }

    public Long incrby(String key, long delta) {
        return this.stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public Long decr(String key) {
        return this.stringRedisTemplate.opsForValue().increment(key, -1L);
    }

    public Long decrby(String key, long delta) {
        return this.stringRedisTemplate.opsForValue().increment(key, -delta);
    }

    public void set(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key, value, this.timeout, TimeUnit.SECONDS);
    }

    public void set(String key, String value, long timeout, TimeUnit unit) {
        this.stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }
}
