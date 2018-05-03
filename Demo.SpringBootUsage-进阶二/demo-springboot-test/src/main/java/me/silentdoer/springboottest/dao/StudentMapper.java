package me.silentdoer.springboottest.dao;

import me.silentdoer.springboottest.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author silentdoer
 */
@Service
public interface StudentMapper {
    /**
     * 通过单主键获取单个Student对象
     * @param uid 主键
     * @return 记录映射成的Student对象
     */
    @Select("select uid, name, gender from student where uid=#{uid}")
    Student getSingleStudentByPrimaryKey(@Param("uid") Long uid);
}
