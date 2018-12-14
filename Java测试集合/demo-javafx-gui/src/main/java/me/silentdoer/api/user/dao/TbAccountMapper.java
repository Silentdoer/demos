package me.silentdoer.api.user.dao;

import me.silentdoer.api.user.model.TbAccount;
import me.silentdoer.api.user.model.TbAccountExample;
import org.springframework.stereotype.Repository;

/**
 * TbAccountMapper继承基类
 */
@Repository
public interface TbAccountMapper extends MyBatisBaseDao<TbAccount, Long, TbAccountExample> {
}