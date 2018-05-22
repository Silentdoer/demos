package me.silentdoer.springbootcacheabletomapper.dao;

import me.silentdoer.springbootcacheabletomapper.model.StudentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Mapper
public interface StudentMapper {

    @Cacheable(value = "students")
    @Select("select " +
            "t.id, t.name, t.gender " +
            "from student t where id=#{id}")
    StudentDO selectStudentById(@Param("id") Long id);
}
