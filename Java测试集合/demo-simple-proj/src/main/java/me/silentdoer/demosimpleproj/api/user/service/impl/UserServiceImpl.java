package me.silentdoer.demosimpleproj.api.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.api.user.dao.UserMapper;
import me.silentdoer.demosimpleproj.api.user.model.UserPo;
import me.silentdoer.demosimpleproj.api.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 9:48 AM
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserPo getUserById(Long id) {
        UserPo userPo = this.userMapper.selectById(id);
        return userPo;
    }

    @Override
    public boolean checkUsername(String username) {
        UserPo userPo = this.userMapper.selectByUsername(username);
        return Objects.isNull(userPo);
    }
}
