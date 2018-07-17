package me.silentdoer.springbootbeannamegeneratorstrategy.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootbeannamegeneratorstrategy.service.IUserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/6/2018 3:42 PM
 */
@RestController
@Slf4j
@RequestMapping
public class TestController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private IUserService userService;

    @GetMapping("/test")
    public Boolean test() {
        log.info(String.valueOf(this.userService == null));
        Object userService = this.applicationContext.getBean("userService");
        return userService == null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
