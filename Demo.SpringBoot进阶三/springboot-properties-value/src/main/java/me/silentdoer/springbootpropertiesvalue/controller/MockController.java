package me.silentdoer.springbootpropertiesvalue.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    /**
     * 有默认值也会被覆盖，即这个值就是路径，但是注意必须得有匹配，否则会报错
     * ，是可以有默认值，但是默认值是在后面加:xx，xx就是默认值，如果没有写默认值的配置那么如果找不到key会报错。
     */
    @Value("${url.hulu.send-uu-mm:http://localhost/{uus}/%3DFE/中文/{ssuu}}")
    public String test = "有默认值呢";

    /**
     * 经过测试静态成员不可以注册Value，虽然没有报错但是获取不到值；哪怕配置了默认值也获取不到默认值
     * TODO 可以这么认为Spring的IOC功能只适用于实例成员，静态成员不适用；（包括所有的装配方式如@Value）
     */
    @Value("${config.datasource.name:sss}")
    private static String mmm;

    /**
     * 经过测试静态成员不能注册bean，且如果用@Resource还会直接报错，而@Autowired即便没有报错也是获取不到正确值的；
     * TODO 哪怕Bean是单个也一样获取不到；
     */
    @Autowired
    private static MockController testBean;

    @GetMapping("/test1")
    public void test1(){
        log.info(StringUtils.join("测试Spring的@Value是否有用，其值为：", foo));
        log.info(StringUtils.join("值二位：", dataSourceName));
        log.info(test);
        log.info(MockController.mmm);
        log.info(String.valueOf(MockController.testBean == null));
    }
}
