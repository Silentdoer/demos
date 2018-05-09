package me.silentdoer.springbootredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.validation.Errors;

/**
 *
 * `@Import`注解和@ImportResource的区别是前者就类似
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)  // 这个60似乎就是session的过期时间
@SpringBootApplication
public class SpringbootRedisApplication {

    /**
     * application.properties里配置的redis的host之类的只是为了自动将HttpSession序列化到redis用的，如果要用RedisTemplate还是得配置RedisConnectionFactory这一些
     * @return
     */
    /*@Bean
    @Scope("prototype")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, Errors errors){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisApplication.class, args);
    }
}
