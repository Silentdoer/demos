package me.silentdoer.springbootservletinputstreamtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class TestController implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @PostMapping("/test1")
    public String test1(HttpServletRequest request) throws IOException {
        log.info("stream size:{}", request.getInputStream().available());
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) this.beanFactory;
        String[] beanNamesForType = beanFactory.getBeanNamesForType(Filter.class);
        log.info("Test:{}", Arrays.asList(beanNamesForType));
        return "OK";
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
