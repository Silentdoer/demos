package me.silentdoer.springbootfilter.api.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author silentdoer
 * @version 1.0
 */
@Data
public class LoginRequest {
    /**
     * `@NotBlank`是要和@Valid注解一起使用
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
