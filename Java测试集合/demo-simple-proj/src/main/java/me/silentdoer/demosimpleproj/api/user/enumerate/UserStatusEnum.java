package me.silentdoer.demosimpleproj.api.user.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户账户的状态
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 9:58 AM
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    INIT(0, "初始状态，比如提交注册申请"), NORMAL(1, "正常可用状态"), DISABLE(-1, "禁用状态");

    private int status;

    private String memo;
}
