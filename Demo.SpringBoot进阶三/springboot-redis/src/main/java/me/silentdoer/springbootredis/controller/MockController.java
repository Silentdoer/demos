package me.silentdoer.springbootredis.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author liqi.wang
 * @version 1.0
 */
@RestController
@Slf4j
public class MockController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 这种情况下会自动request.getSession()然后再传这个session给mapping方法（已经过测试）
     *
     * @param httpSession
     * @return
     */
    @GetMapping("/test1")
    public ResponseEntity<String> test1(HttpSession httpSession){
        // TODO 会在redis里产生三个key-value，而且只有一个是String类型的pair，且值测出来的是""
        // TODO 重要，自己并没有@EnableRedisHttpSession居然也可以自动将session序列化到redis里，查下为什么？？

        // TODO 经过测试，将session对象序列化存储到redis里是在产生session对象时就已经做了，而不是要等到session.setAttribute(..)才序列化到redis里

        // TODO 经过测试这个httpSession并不是由request.getSession()获得的，这里是外部会判断httpSession是否有set，有才request.getSession()，但是set到redis确实是在外面就有了
        httpSession.setAttribute("userId", RandomStringUtils.randomNumeric(16));
        return ResponseEntity.ok("success");
    }

    /**
     * 经过测试时间到了在redis里其实也只是消除了部分的pair，还有个是没有消除的貌似要主动消除？？
     * @param httpSession
     * @return
     */
    @GetMapping("/test2")
    public ResponseEntity<String> test2(HttpSession httpSession){
        // TODO 可以获得，废话，这都是从session里获取肯定可以，由于
        return ResponseEntity.ok(httpSession.getAttribute("userId").toString());
    }

    /**
     *
     * @param httpSession
     */
    @GetMapping("/test3")
    public void test3(HttpSession httpSession){
        // TODO 这个并不会造成序列化在redis里的三个pair消失，应该只是修改了内部的值而已
        httpSession.removeAttribute("userId");
    }

    @GetMapping("/test4")
    public void test4(HttpSession httpSession){
        log.info(StringUtils.join(redisTemplate.keys("*").size()));
    }

    @GetMapping("/test5")
    public String test5() {
       // this.redisTemplate.opsForValue().set("silentdoer", "理解额77");
        Object silentdoer = this.redisTemplate.opsForValue().get("silentdoer");
        log.error((silentdoer == null) + "");
        return silentdoer.toString();
    }
}
