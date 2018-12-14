package me.silentdoer.springbootfilter.api.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author silentdoer
 * @version 1.0
 */
@Data
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
