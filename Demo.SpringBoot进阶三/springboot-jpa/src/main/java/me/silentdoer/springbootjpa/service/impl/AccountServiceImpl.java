package me.silentdoer.springbootjpa.service.impl;

import me.silentdoer.springbootjpa.dao.AccountDao;
import me.silentdoer.springbootjpa.model.Account;
import me.silentdoer.springbootjpa.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.Objects;

/**
 *
 * @author liqi.wang
 * @version 1.0.0
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getSingleAccount(@Max(value = 3, message = "不能超过5用于测试") Long id) {
        //this.accountDao.getOne(id);
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("主键未提供");
        }
        return this.accountDao.findById(id).orElse(null);
    }
}
