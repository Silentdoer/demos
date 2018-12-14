package me.silentdoer.springbootcontrolleradvice.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class FooModel extends BaseModel{
    private String name;
    private Character gender;
}
