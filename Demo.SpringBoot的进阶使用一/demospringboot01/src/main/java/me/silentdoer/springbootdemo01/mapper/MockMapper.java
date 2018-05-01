package me.silentdoer.springbootdemo01.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 1:27 PM
 */
//@Repository  // 这两个注解都可以写上，但经过测试其实只需要@Mapper即可（或者通过MapperScan来扫描路径，可以配置在@SpringBootApplication下面）
@Mapper
public interface MockMapper {
    /**
     也可以在application.yml中配置：
     mybatis:
     mapper-locations: classpath:mybatis/mapper/*.xml  // 有了下面的其实这个不要也行，因为config里可以提供mapper.xml的文件路径
     config-location: classpath:mybatis/mybatis-config.xml
     */
    @Select("select name from student where uid = #{uid}")
    String getSingleName(@Param("uid") Long uid);
    /**
     * 没有配置任何mybatis的配置却能直接用mybatis是因为spring boot预留了一个
     * 包路径作为插件路径（就像notepad++只要将插件放到plugins目录就能自动被notepad++加载）
     * ，比如spring boot预留了org.springframework.boot.plugin.bean这个包为插件bean的路径，那么mybatis的依赖包只需要
     * 创建一个属于这个包的bean即可，然后spring boot自动就会扫描到并添加到IOC容器里
     */
}
