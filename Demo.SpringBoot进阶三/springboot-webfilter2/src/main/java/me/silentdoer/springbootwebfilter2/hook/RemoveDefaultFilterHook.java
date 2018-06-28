package me.silentdoer.springbootwebfilter2.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Component
@Slf4j
public class RemoveDefaultFilterHook implements BeanDefinitionRegistryPostProcessor {

    /**
     * 在所有bean创建之前执行（hook类型的bean除外）
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        boolean hiddenHttpMethodFilter = registry.containsBeanDefinition("hiddenHttpMethodFilter");
        BeanDefinition beanDefinition = registry.getBeanDefinition("hiddenHttpMethodFilter");
//        registry.removeBeanDefinition("hiddenHttpMethodFilter");
        log.error(Boolean.toString(hiddenHttpMethodFilter));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
