package me.silentdoer.test;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author silentdoer
 * @version 1.0
 */
public class MockRedisTemplate {
    public static void main(String[] args){
        RedisTemplate redisTemplate = new RedisTemplate();
        // opsForValue()是操作Redis中的String类型，还有opsForList()之类的
        redisTemplate.opsForValue().get("key");
        Long aLong = redisTemplate.opsForList().rightPush("key", "value");
    }
}
