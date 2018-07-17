package me.silentdoer.mybatisinterceptorusage.dao;

import me.silentdoer.mybatisinterceptorusage.model.StudentDo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 3:39 PM
 */
@Mapper
@Repository
public interface StudentMapper {

    @Select("select t.* from tb_student t where fd_name = #{name}")
    /**
     * 这个Results就充当了.xml里的ResultMap，而Result的Target为空，那么就只能当做另一个注解的成员注解值使用
     */
    @Results({
                     @Result(id = true, property = "id", column = "fd_id"),
                     @Result(property = "name", column = "fd_name"),
                     @Result(property = "gender", column = "fd_gender")
             })
    List<StudentDo> selectRepeatableNameStudent(@Param("name") String name);
}
