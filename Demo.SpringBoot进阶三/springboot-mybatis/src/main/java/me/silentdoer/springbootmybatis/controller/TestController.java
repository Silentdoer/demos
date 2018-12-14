package me.silentdoer.springbootmybatis.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootmybatis.dao.StudentNoAnnotationMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@RestController
public class TestController implements BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @GetMapping("/test99")
    public void test99(){
        /**
         * 经过测试无论是否有@Repository或者@Repository的名字和默认不一样也都只是生成一个bean
         */
        String[] beanNamesForType = this.beanFactory.getBeanNamesForType(StudentNoAnnotationMapper.class);
        log.info(Arrays.asList(beanNamesForType).toString());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = ((DefaultListableBeanFactory) beanFactory);
    }
}
