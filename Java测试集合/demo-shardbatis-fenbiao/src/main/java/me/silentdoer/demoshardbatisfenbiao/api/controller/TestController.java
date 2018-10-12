package me.silentdoer.demoshardbatisfenbiao.api.controller;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demoshardbatisfenbiao.api.dao.UserSignedHistoryMapper;
import me.silentdoer.demoshardbatisfenbiao.api.model.TbUserSignedHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 6:11 PM
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private UserSignedHistoryMapper userSignedHistoryMapper;

    @GetMapping("/api/common/insert")
    public String insert(@Param("userId") Long userId) {
        TbUserSignedHistory record = new TbUserSignedHistory();
        record.setFdUserId(userId);
        int idx = this.userSignedHistoryMapper.insertSelective(record);
        return String.valueOf(idx);
    }

    @GetMapping("/api/common/select")
    public String select(@Param("userId") Long userId) {
        TbUserSignedHistory record = userSignedHistoryMapper.selectByUserId(userId);
        return String.valueOf(record.getFdUserId());
    }
}
