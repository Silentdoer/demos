package me.silentdoer.springbootmappingannotationusage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long fId;
    private String fName;
    private Character fGender;
}
