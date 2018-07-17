package me.silentdoer.mybatisinterceptorusage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 3:41 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDo {

    private Long id;

    private String name;

    private Character gender;
}
