package me.silentdoer.demosimpleproj.api.user.dao;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.api.user.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 6:16 PM
 */
@Repository
@Mapper
public interface UserMapper {

    @Select("select * from tb_user where fd_id = #{id} and fd_status = 1")
    User selectById(@Param("id") Long id);

    @Select("select * from tb_user where fd_username = #{username} and fd_status = 1")
    User selectByUsername(@Param("username") String username);

    @Insert("insert into tb_user(fd_username, fd_password, fd_salt, fd_status, fd_cellphone, fd_email, fd_pay_password, fd_register_time," +
                    "fd_token, fd_client_id) values(#{fdUsername}, #{fdPassword}, #{fdSalt}, #{fdStatus}, #{fdCellphone}, #{fdEmail}, #{fdPayPassword}," +
                    "#{fdRegisterTime}, #{fdToken}, #{fdClientId})")
    @Options(useGeneratedKeys = true, keyColumn = "fd_id", keyProperty = "fdId")
    void insert(User user);
}
