package me.silentdoer.springbootcache.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcache.model.Student;
import me.silentdoer.springbootcache.service.MockService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当Controller里有多种RequestMethod时就用RequestMapping而不要用GetMapping之类的
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@RequestMapping("/mock")
@Slf4j
public class MockController implements BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Autowired
    private MockService mockService;

    @GetMapping("/test1")
    public Student test1(Long arg){
        log.info("test1");
        String[] beanNamesForType = this.beanFactory.getBeanNamesForType(RedisCacheManager.class);
        log.info(beanNamesForType.length + "");
        RedisCacheManager cacheManager = ((RedisCacheManager) this.beanFactory.getBean(beanNamesForType[0]));
        System.out.println(cacheManager.getCacheNames());
        // getCache如果不存在会创建（类似redis切换db一样可以随意切换）
        // TODO 获取成功，说明确实是配置了redis那么就用的是RedisCacheManager来管理，而且@Cacheable的value确实是定义一个在spring里管理的cache表的某个cache的名称
        // TODO 只不过所有的cache的所有key写在redis里时都写在一个dbIdx里而且还会合并（配置了key-prefix的前提下），基于此其实不要配置key-prefix比较好
        // TODO students可以用cacheNames中的别名替换
        System.out.println(cacheManager.getCache("students").get("me.silentdoer.springbootcache.service.MockService#doService#77,ui").get());
        return this.mockService.doService(arg, "ui");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = ((DefaultListableBeanFactory) beanFactory);
    }
}
