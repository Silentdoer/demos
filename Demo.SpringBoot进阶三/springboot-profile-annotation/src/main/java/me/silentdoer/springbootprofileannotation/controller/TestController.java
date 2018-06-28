package me.silentdoer.springbootprofileannotation.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
@Profile({"prod"})
public class TestController implements ApplicationContextAware {
    private AbstractApplicationContext applicationContext;

    @Value("${silentdoer}")
    private String test = "UUUUUUUU";

    @GetMapping("/test1")
    public String test1() {
        boolean studentAAA = this.applicationContext.containsBeanDefinition("studentAAA");
        return studentAAA + "" + test;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = ((AbstractApplicationContext) applicationContext);
    }
}
