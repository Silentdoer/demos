package me.silentdoer.ssmdemo.dao;

import me.silentdoer.ssmdemo.pojo.Student;
import org.apache.ibatis.annotations.Param;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 1:40 PM
 */
public interface StudentDao {
    Student selectOne(@Param("uid") long uid);
}
