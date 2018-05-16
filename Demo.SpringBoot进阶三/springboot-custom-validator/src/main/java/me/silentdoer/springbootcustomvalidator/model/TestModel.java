package me.silentdoer.springbootcustomvalidator.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Accessors(chain = true)
@Data
public class TestModel {
    @Length(min = 12, max = 18, message = "密码不符合要求")
    private String password;
}
