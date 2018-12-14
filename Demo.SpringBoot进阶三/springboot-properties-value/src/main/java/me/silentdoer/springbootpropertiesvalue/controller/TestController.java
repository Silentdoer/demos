package me.silentdoer.springbootpropertiesvalue.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 2018/6/27 15:26
 */
@Slf4j
@RestController
public class TestController {
    // TODO 重要，在其它地方用了@PropertyResource后，另一个地方是可以不再用的，不过为了可读性也可以再次@PropertyResource
    @Value("${config.datasource.name}")
    private String foo;

    @GetMapping("/test31")
    public String test31() {
        return this.foo;
    }
}
