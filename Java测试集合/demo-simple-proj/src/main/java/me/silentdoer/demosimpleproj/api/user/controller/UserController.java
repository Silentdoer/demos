package me.silentdoer.demosimpleproj.api.user.controller;

import me.silentdoer.demosimpleproj.api.common.enumerate.SmsRedisEnum;
import me.silentdoer.demosimpleproj.api.user.model.User;
import me.silentdoer.demosimpleproj.api.user.model.req.RegisterReq;
import me.silentdoer.demosimpleproj.api.user.model.resp.LoginResp;
import me.silentdoer.demosimpleproj.core.component.EnvironmentFacade;
import me.silentdoer.demosimpleproj.core.component.StringRedisTemplateFacade;
import me.silentdoer.demosimpleproj.core.support.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/28/2018 10:49 AM
 */
@RestController
// 这个路径的命名其实就是可以和api/user一样，其他的是/api/common；/api/application；/api/backstage；等等，最多再加个版本号
// 不用像小贷一样前面还要加一个microloan，因为如果是jar包那么显然它的端口是独立的，如果是war包那么war包名已经充当了microloan的路径
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private StringRedisTemplateFacade stringRedisTemplateFacade;

    @Autowired
    private EnvironmentFacade environmentFacade;

    @PostMapping("/register")
    public ApiResult<LoginResp> register(@RequestBody @Valid RegisterReq req) {
        String redisKey = SmsRedisEnum.REGISTER.getRedisKeyTemplate().replace("{}", req.getUsername());
        String value = stringRedisTemplateFacade.get(redisKey);
        if (StringUtils.isBlank(value)) {
            return ApiResultEnum.failTip("短信过期，请重新获取");
        }
        return ApiResultEnum.ok();
    }

    @GetMapping("/registerSmsCode")
    @RequiresLogin
    public ApiResult<Object> registerSmsCode(@RequestParam("username") String username) {

        String redisKey = SmsRedisEnum.REGISTER.getRedisKey(username);
        // TODO 要将发送短信的数据进行数据库的相关Log表记录
        // TODO 判断是否允许再次发送验证码（频繁发送），这里其实还应该加分布式锁，防止对方并发调用此接口
        // TODO 判断是否是debug环境
        if (environmentFacade.isDebugEnv()) {
            // TODO 固定验证码000000，这里要对redisTemplate封装一下，防止不设置过期时间（封装后如果不设置默认是15天过期）
            stringRedisTemplateFacade.set(redisKey, "000000", 5, TimeUnit.MINUTES);
        } else {

        }

        return ApiResultEnum.ok();
    }
}
