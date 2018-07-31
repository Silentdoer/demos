package me.silentdoer.springbootsessionidgeneratestrategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
// import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@SpringBootApplication
/**
 * 注意只要添加了相关的jar包，即便没有这个注解或者没有@EnableRedisHttpSession但其实HttpSession已经是Spring自定义的了
 * ，而且JSESSIONID也变成了SESSION（就跟redis一样没有配置但其实redis也已经生效了）
 */
//@EnableSpringHttpSession
//@EnableRedisHttpSession
public class SpringbootSessionidGenerateStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSessionidGenerateStrategyApplication.class, args);
    }
}
