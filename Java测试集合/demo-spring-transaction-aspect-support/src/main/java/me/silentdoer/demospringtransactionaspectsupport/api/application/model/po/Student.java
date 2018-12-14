package me.silentdoer.demospringtransactionaspectsupport.api.application.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/27/2018 11:49 AM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long fdId;

    private String fdName;

    private Integer fdGender;

    private String fdClassId;
}
