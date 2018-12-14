package me.silentdoer.demomysqlcharsettest.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/6/2018 8:27 PM
 */
@Mapper
@Repository
public interface StudentMapper {

    @Insert("insert into tb_student(fd_name, fd_gender, fd_class_id) values('双方都', 3, 'sfsf8M')")
    void insert();
}
