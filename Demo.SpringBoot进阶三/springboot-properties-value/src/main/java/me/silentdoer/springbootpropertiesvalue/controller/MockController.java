package me.silentdoer.springbootpropertiesvalue.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@RestController
// 貌似默认就是UTF-8，但是最好还是显式指定；如果@Value不需要扫描.properties文件那么可以不用这个注解
@PropertySource(value = {"classpath:config/pair.properties"}, encoding = "UTF-8")
public class MockController {
    /**
     * `@Value`就是由Spring扫描动态的将@Value中的value成员的值赋予foo，这里是用字面量来赋值，还可以通过${config.key1}来获取配置文件的值（properties/可以）
     * xx.yml的不可以，或者说即便是yml的Spring在@Value时也将xx.yml认为是xx.properties文件
     *
     * 这种字面量的形式是不需要@PropertySource的
     */
    @Value("Hello, world.")
    private String foo;

    /**
     * 必须在其所在的类上面加上@PropertySource否则会报找不到key的错误
     */
    @Value("${config.datasource.name}")
    private String dataSourceName;

    @GetMapping("/test1")
    public void test1(){
        log.info(StringUtils.join("测试Spring的@Value是否有用，其值为：", foo));
        log.info(StringUtils.join("值二位：", dataSourceName));
    }
}
