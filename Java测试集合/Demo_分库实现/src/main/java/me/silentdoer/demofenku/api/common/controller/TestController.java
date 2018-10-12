package me.silentdoer.demofenku.api.common.controller;

import me.silentdoer.demofenku.api.common.db.dao.UserMapper;
import me.silentdoer.demofenku.api.common.db.model.User;
import me.silentdoer.demofenku.api.common.service.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 12:04 PM
 */
@RestController
@RequestMapping("/user")
public class TestController {

    @Resource
    private IUserService userService;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    /**
     * 测试插入
     */
    @RequestMapping("/add")
    public String add(Long id, String userName) {
        User u = new User();
        u.setFdId(id);
        u.setFdUserName(userName);
        this.userService.insertUser(u);
        return u.getFdId() + "    " + u.getFdUserName();
    }

    /**
     * 测试读
     */
    @RequestMapping("/get/{id}")
    public String findById(@PathVariable("id") Long id) {
        User u = this.userService.findById(id);
        return u.getFdId() + "    " + u.getFdUserName();
    }

    /**
     * 测试写然后读
     */
    @RequestMapping("/addAndRead")
    public String addAndRead(Long id, String userName) {
        User u = new User();
        u.setFdId(id);
        u.setFdUserName(userName);
        this.userService.writeAndRead(u);
        return u.getFdId() + "    " + u.getFdUserName();
    }

    /**
     * 测试读然后写
     */
    @RequestMapping("/readAndAdd")
    public String readAndWrite(Long id, String userName) {
        User u = new User();
        u.setFdId(id);
        u.setFdUserName(userName);
        this.userService.readAndWrite(u);
        return u.getFdId() + "    " + u.getFdUserName();
    }

    @RequestMapping("/listUser")
    public List<User> listUser() {
        return this.userMapper.count("silentdoer");
    }
}