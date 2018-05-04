package me.silentdoer.springbootmybatis.service;

import me.silentdoer.springbootmybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author silentdoer
 * @version 1.0
 */
public interface StudentService {
    Student getSingleStudent(@Param("uid") Long uid);
    //List<Student> getStudents(@Param("leftIdx") )
}
