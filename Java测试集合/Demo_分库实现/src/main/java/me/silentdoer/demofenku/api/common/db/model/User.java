package me.silentdoer.demofenku.api.common.db.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 12:05 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long fdId;

    private String fdUserName;
}