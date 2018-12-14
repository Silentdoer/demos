package me.silentdoer.springbootvalidannotation.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author silentdoer
 * @version 1.0
 */
@Data
public class Student implements Serializable {
    private Long uid;
    @Pattern(regexp = "^\\w*$", message = "名字只能是\\w")
    private String name;
    //@NotBlank(message = "gender不能为空白")  // NotBlank不能用于Character
    @NotNull(message = "性别不能为null")
    private Character gender;
}
