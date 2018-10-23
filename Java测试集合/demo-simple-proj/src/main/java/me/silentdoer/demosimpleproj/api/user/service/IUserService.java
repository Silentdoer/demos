package me.silentdoer.demosimpleproj.api.user.service;

import me.silentdoer.demosimpleproj.api.user.model.UserPo;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 9:47 AM
 */
public interface IUserService {

    UserPo getUserById(Long id);

    /**
     * 检测用户名是否唯一
     */
    boolean checkUsername(String username);
}
