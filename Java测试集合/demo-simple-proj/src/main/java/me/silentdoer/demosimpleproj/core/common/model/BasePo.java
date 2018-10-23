package me.silentdoer.demosimpleproj.core.common.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 9:54 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasePo {

    protected Long fdId;

    protected Date fdCreateTime;

    protected Date fdUpdateTime;
}
