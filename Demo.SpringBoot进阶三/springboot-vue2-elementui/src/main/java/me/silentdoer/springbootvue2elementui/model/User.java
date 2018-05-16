package me.silentdoer.springbootvue2elementui.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class User {
    private Integer userId;
    private String username;
    private Byte sex;
    private Date createTime;

    public User setUserId(Integer userId){
        this.userId = userId;
        // TODO 返回一个新的Date对象，基于new Date() - userId个小时
        this.createTime = DateUtils.addHours(new Date(), -userId);
        return this;
    }
}
