package me.silentdoer.springbootshiro.model;

import lombok.*;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 用户所有角色值，用于shiro做角色权限的判断
     */
    private Set<String> roles;

    /**
     * 用户所有权限值，用于shiro做资源权限的判断
     */
    private Set<String> perms;
}
