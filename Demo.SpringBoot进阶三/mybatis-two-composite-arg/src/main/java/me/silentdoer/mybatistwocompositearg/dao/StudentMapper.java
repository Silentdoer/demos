package me.silentdoer.mybatistwocompositearg.dao;

import me.silentdoer.mybatistwocompositearg.model.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Repository
@Mapper
public interface StudentMapper {
    @Insert("insert into student(name, gender, class_id) values(#{student.name}, #{student.gender}, #{classId})")
    void insertStudent(@Param("student") Student student, @Param("classId") Long classId);
}
