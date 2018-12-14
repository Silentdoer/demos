package me.silentdoer.springbootpagehelper.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
