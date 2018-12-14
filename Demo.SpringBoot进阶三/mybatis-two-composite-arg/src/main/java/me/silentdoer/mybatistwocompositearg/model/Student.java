package me.silentdoer.mybatistwocompositearg.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class Student {
    private String name;
    private Character gender;
}
