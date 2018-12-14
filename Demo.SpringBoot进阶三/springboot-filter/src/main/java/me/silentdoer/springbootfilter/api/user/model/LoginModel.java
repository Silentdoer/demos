package me.silentdoer.springbootfilter.api.user.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @author silentdoer
 * @version 1.0
 */
@Accessors(chain = true)
@Data
public class LoginModel {
    // TODO 这里的blank除了不能为null，空字符串，还包括这个字符串里不能都是空白符
    @NotBlank(message = "名字不能为空白")
    private String name;
    @Email(message = "邮箱格式错误")
    private String email;
    @Max(value = 120, message = "年龄最大值不能超过120")
    private Integer age;
}
