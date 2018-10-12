package me.silentdoer.demofenku.api.common.db.dao;

import me.silentdoer.demofenku.api.common.db.model.User;
import me.silentdoer.demofenku.support.db.ReadDataSource;
import me.silentdoer.demofenku.support.db.WriteDataSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 12:05 PM
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert("insert tb_user(fd_id, fd_user_name) values(#{fdId}, #{fdUserName})")
    //@ReadDataSource
    int insert(User user);

    @Select("select fd_id, fd_user_name from tb_user where fd_id = #{fdId}")
    User findById(@Param("fdId") Long id);

    /**
     * 注意这个方法的Sql配置是在.xml里的
     */
    List<User> count(@Param("fdUserName") String userName);
}