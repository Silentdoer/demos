package me.silentdoer.springbootredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * `@Import`注解和@ImportResource的区别是前者就类似
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)  // 这个60就是session的过期时间（在redis里session过期后还是会保留一段时间的，只不过正常模式无法访问）
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

    /**
     * 如果要让IDEA不提示unchecked，只能添加这个注解实现，配置-Xlint:unchecked只能在编译的时候不弹message而已；
     * @param map
     */
    @SuppressWarnings("unchecked")
    private static void test(Map map){
        // TODO map那里会提示unchecked
        // TODO 用一个final局部变量来对应参数似乎已经过时，可以不这么做；
        final Map<String, String> m2 = map;
        m2.put("uuu", "sss");
        // TODO 但是静态成员在静态方法里用一个final变量去对应却是推荐的
        final String tes = SpringbootRedisApplication.ss;
        System.out.println(tes);
        System.out.println(m2.size());
    }

    private static String ss = "8888";

    private String mm = "KKKKKKK";

    public void test(){
        // TODO 实例成员在成员方法里也一般会用一个final变量来对应（推荐）
        final String st = this.mm;
        System.out.println(st);
    }
}
