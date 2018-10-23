package me.silentdoer.demosimpleproj.api.user.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 5:36 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "username is null")
    private String username;

    @NotBlank(message = "password is null")
    private String password;

    @NotBlank(message = "verifyCode is null")
    private String verifyCode;
}
