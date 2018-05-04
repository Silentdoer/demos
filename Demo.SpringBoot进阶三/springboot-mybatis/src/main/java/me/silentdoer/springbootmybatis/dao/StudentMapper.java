package me.silentdoer.springbootmybatis.dao;

import me.silentdoer.springbootmybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 也可以通过@Mapper而不用@MapperScan
 *
 * @author silentdoer
 * @version 1.0
 */
public interface StudentMapper {
    /**
     * 通过主键获取一个student对象
     * @param uid student表的主键
     * @return 获得的Student对象
     */
    @Select("select uid, name, gender from student where uid=#{uid}")
    Student selectStudentByPrimaryKey(@Param("uid") Long uid);
}
