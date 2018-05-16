package me.silentdoer.springbootcache.service;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootcache.model.Student;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
@Slf4j
public class MockService {

    /**
     * `@Cacheable`的value是一个缓存的group名
     * TODO 默认情况下key就是#arg，这种方式会生成students::33（33是arg的值，即不同的参数会分别缓存返回值）
     * TODO 如果是有多个参数时，它在redis里key是：students:SimpleKey [arg, aa]（arg和aa分别用对应的参数值替换）
     *
     * TODO key = "#arg"表示key部分取arg的值（value是group部分）
     *
     * TODO key支持SpEL；即Spring Expression Language，即内部可以写代码，向pointcut的值也是支持的，内部还可以有||，&&，+等符号，还能使用简单的JDK Api方法
     * 这个 args是参数组成的“数组”，但是输出是：77,uuu的格式而不是一个对象
     *
     * TODO 由于这个类是singleton，因此用targetClass而无需target（target表示调用此方法的类对象，即MockService对象）
     *
     * TODO 这个value值在application.properties里配置了spring.cache.redis.key-prefix=xxx后会失效（因此最好不要用value，只用key即可，students写到key里，错误总结）
     * 而cacheNames似乎是value的别名？？
     *
     * 注意这个生产key时用到所有参数，跟参数是否被用到无关，因为运行时代码是不知道的只是IDE能检测而已；
     *
     * TODO condition可以用SpEL来写，只有当其值为true时才缓存
     * @param arg
     * @return
     */
    @Cacheable(value = "students", key="#root.targetClass.name.concat('#') + #root.methodName.concat('#') + #root.args", condition = "true")
    public Student doService(Long arg, String aa){
        log.info("doService".concat(arg.toString()));
        return new Student().setUid(arg).setName("default");
    }
}
