package me.silentdoer.springbootcontrolleradvice.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class BaseModel {
    private Long uid;
    private Timestamp createTime;
    private Timestamp modifyTime;
}
