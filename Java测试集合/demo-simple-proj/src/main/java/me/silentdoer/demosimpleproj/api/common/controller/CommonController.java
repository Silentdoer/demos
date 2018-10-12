package me.silentdoer.demosimpleproj.api.common.controller;

import me.silentdoer.demosimpleproj.api.common.enumerate.SmsRedisEnum;
import me.silentdoer.demosimpleproj.api.user.model.User;
import me.silentdoer.demosimpleproj.api.user.service.IUserService;
import me.silentdoer.demosimpleproj.core.component.EnvironmentFacade;
import me.silentdoer.demosimpleproj.core.component.StringRedisTemplateFacade;
import me.silentdoer.demosimpleproj.core.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 6:45 PM
 */
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private EnvironmentFacade environmentFacade;

    /**
     * 在SpringBoot里默认是存在这个对象的
     */
    @Autowired
    private StringRedisTemplateFacade stringRedisTemplateFacade;

    @Autowired
    private IUserService userService;

    /**
     * 获取验证码，根据不同的场景将验证码缓存到redis的不同key里
     */
    @GetMapping("/smsCode")
    @RequiresLogin
    public ApiResult<Object> smsCode(@RequestParam(value = "scene", required = true) Integer scene,
                                     @RequestHeader(value = Api.APP_KEY, required = true) String appKey) {

        Long fUserId = Long.valueOf(JwtUtils.getTokenKey(appKey));
        User user = userService.getUserById(fUserId);

        SmsRedisEnum correspondSceneEnum = SmsRedisEnum.getCorrespondSceneEnum(scene);
        if (Objects.isNull(correspondSceneEnum)) {
            throw new ApiInvalidArgException("未找到相关业务场景");
        }
        String redisKey = correspondSceneEnum.getRedisKey(user.getFdUsername());
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
