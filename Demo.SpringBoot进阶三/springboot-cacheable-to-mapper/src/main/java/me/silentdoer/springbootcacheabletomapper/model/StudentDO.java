package me.silentdoer.springbootcacheabletomapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDO {
    private Long id;
    private String name;
    private Character gender;
}
