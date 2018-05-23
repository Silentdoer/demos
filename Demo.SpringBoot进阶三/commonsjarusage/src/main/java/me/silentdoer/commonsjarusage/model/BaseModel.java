package me.silentdoer.commonsjarusage.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class BaseModel {
    private Long fId;
    private String fName;
}
