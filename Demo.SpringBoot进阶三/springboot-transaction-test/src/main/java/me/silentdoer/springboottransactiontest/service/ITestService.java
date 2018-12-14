package me.silentdoer.springboottransactiontest.service;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/9/2018 6:30 PM
 */
public interface ITestService {
    int addStudent(String name, Character gender, Boolean rollback);
}
