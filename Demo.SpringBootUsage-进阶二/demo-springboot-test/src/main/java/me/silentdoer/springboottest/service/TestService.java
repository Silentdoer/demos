package me.silentdoer.springboottest.service;

import org.springframework.stereotype.Service;

/**
 * @author silentdoer
 * @version 1.0
 */
@Service
public interface TestService {
    void logicFunc();
    void recover(Exception ex);
    String cacheReturnValue();
}
