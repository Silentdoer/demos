package me.silentdoer.springbootjdbc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行成功，说明用JdbcTemplate只需要配置最基础的如url/username/password即可，不过最好还是配上driver-class-name
     *
     * @return
     */
    @GetMapping("/test1")
    public String test1(@RequestHeader("id") Long id) {
        // TODO 不再需要创建Connection对象和Statement对象然后执行，然后自己逐步获取每条记录的每个字段，这里JdbcTemplate对象能自动完成前置工作和自动帮我们将结果转换为对应的对象
        String result = this.jdbcTemplate.queryForObject("select name from student where id=?", new Object[]{id}, String.class);
        return result;
    }
}
