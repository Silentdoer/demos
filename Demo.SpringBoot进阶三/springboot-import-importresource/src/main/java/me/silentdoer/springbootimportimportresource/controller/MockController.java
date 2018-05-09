package me.silentdoer.springbootimportimportresource.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Slf4j
@RestController
public class MockController implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @GetMapping("/test1")
    public void test1(){
        log.info(this.beanFactory.getBean("foo").toString());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
