package me.silentdoer.demoshardbatisfenbiao.api.dao;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demoshardbatisfenbiao.api.model.TbUserSignedHistory;
import org.springframework.stereotype.Repository;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 6:12 PM
 */
@Repository
public interface UserSignedHistoryMapper {

    TbUserSignedHistory selectByUserId(Long fdId);

    int insertSelective(TbUserSignedHistory record);
}
