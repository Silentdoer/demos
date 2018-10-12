package me.silentdoer.demosimpleproj.core.component;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.core.support.IAppAuthHandler;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 4:21 PM
 */
public abstract class AbstractAppAuthHandler implements IAppAuthHandler {

    private int order;

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
