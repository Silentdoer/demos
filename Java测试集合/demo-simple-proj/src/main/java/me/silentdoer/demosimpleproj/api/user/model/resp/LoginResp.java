package me.silentdoer.demosimpleproj.api.user.model.resp;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 注册成功和登录成功都用这个Model，
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 5:17 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    private String foo;
}
