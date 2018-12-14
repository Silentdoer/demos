package me.silentdoer.springbootcustomvalidator.model;

import lombok.Data;
import lombok.experimental.Accessors;
import me.silentdoer.springbootcustomvalidator.valid.MyRange;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class MockModel2 {
    @MyRange(min = 2, max = 5, message = "不合要求的值Integer")
    private Integer age;
}