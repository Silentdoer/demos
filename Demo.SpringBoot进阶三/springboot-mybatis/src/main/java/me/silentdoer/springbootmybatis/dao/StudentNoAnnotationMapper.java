package me.silentdoer.springbootmybatis.dao;

import me.silentdoer.springbootmybatis.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author silentdoer
 * @version 1.0
 */
// TODO 对应BeanDefinition的beanName值以@Repository指定的为准，如果没有@Repository那么Mybatis会将生成BeanDefinition和bean的工作都做了
// TODO，有@Repository那么生成BeanDefinition由spring做
@Repository("student333NoAnnotationMapper")
@Mapper
public interface StudentNoAnnotationMapper {
    /**
     * 注意，尽管MapperScan会扫描，但是优先使用自定义的XxMapper.xml的配置（TODO 通过修改StudentNoAnnotationMapper.xml的配置确实是.xml文件生效了）
     * 或者说，@MapperScan只会扫描有如@Select @Delete之类的注解存在的接口为之生成bean由spring的IOC容器管理
     * @param uid
     * @return
     */
    Student selectStudentByPrimaryKey(@Param("uid") Long uid);
}
