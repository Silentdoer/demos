package me.silentdoer.demomybatissqlsessiontest.api.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import me.silentdoer.demomybatissqlsessiontest.api.test.model.po.Student;

@Mapper
public interface StudentMapper {
    int insert(@Param("student") Student student);

    int insertSelective(@Param("student") Student student);

    int insertList(@Param("students") List<Student> students);

    int update(@Param("student") Student student);

    Student selectById(@Param("id") Long id);
}
