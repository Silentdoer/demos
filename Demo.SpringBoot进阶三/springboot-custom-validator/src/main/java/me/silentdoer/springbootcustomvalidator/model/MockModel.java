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
public class MockModel {
    @MyRange(min = 2, max = 4, message = "不合要求的值")
    private String age;
}
