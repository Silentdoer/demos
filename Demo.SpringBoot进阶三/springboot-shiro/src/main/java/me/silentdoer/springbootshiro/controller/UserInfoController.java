package me.silentdoer.springbootshiro.controller;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootshiro.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Controller
@Slf4j
public class UserInfoController {

    @PostMapping("/login")
    public ResponseEntity<UserInfo> login(String userName, String password) {
        // TODO 原来Shiro生效是需要在Mapping方法里用到Shiro？就像PageHelper一样
        Subject currentUser = SecurityUtils.getSubject();
        FormAuthenticationFilter
        //登录
        try {
            currentUser.login(new UsernamePasswordToken(userName, password));
        } catch (IncorrectCredentialsException i) {
            throw new IllegalArgumentException("密码输入错误");
        }
        //从session取出用户信息
        UserInfo user = (UserInfo) currentUser.getPrincipal();
        return ResponseEntity.ok(user);
    }
}
