package me.silentdoer.demofenku.api.common.service;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demofenku.api.common.db.model.User;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 12:04 PM
 */
public interface IUserService {

    void insertUser(User u);

    void writeAndRead(User u);

    void readAndWrite(User u);

    User findById(Long id);
}
