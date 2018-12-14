package me.silentdoer.demointerceptorprinciple.support;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 7:16 PM
 */
public interface PluginInterface {

    void execute1();

    void execute2(Long num);
}
