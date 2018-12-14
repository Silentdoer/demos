package me.silentdoer.springbootjpa.service;

import me.silentdoer.springbootjpa.model.Account;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public interface IAccountService {
    Account getSingleAccount(Long id);
}
