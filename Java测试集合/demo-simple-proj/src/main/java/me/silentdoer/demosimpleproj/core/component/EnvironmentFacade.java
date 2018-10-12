package me.silentdoer.demosimpleproj.core.component;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 7:09 PM
 */
@Component
public class EnvironmentFacade {

    private static final String PROD_ENV_SYMBOL = "prod";

    @Autowired
    private Environment environment;

    public boolean isDebugEnv() {
        return !isProdEnv();
    }

    public boolean isProdEnv() {
        String[] profiles = this.environment.getActiveProfiles();
        if (ArrayUtils.isEmpty(profiles)) {
            return true;
        }
        for (String foo : profiles) {
            if (StringUtils.equals(foo, PROD_ENV_SYMBOL)) {
                return true;
            }
        }
        return false;
    }
}
