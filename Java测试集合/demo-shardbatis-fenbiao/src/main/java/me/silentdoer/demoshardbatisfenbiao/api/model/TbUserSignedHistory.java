package me.silentdoer.demoshardbatisfenbiao.api.model;

import lombok.Data;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 6:25 PM
 */
@Data
public class TbUserSignedHistory {

    private Long fdId;

    private Date fdCreateTime;

    private Date fdModifyTime;

    private Long fdUserId;
}
