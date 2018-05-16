package me.silentdoer.springbootvue2elementui.controller;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootvue2elementui.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(name = "用于测试Vue2+Element-UI")
public class TestController {
    /**
     * pageSize可以由后台定
     * 返回为Map也会解析为JSON字符串，即{"list":[..],"count":5}
     * @param pageNum
     * @param pageSize
     * @param username
     * @return
     */
    @GetMapping("/getTableData")
    public Map<String, Object> getTableData(int pageNum, int pageSize, String username){
        log.info("getTableData called.");
        List<User> users = new LinkedList<>();
        // 1 是男，其它的是女，userId在前台没有用到
        users.add(new User().setUserId(1).setUsername("aaa").setSex(Byte.decode("1")));
        users.add(new User().setUserId(8).setUsername("mmm").setSex(Byte.decode("30")));
        users = username == null ? users : users.stream().filter(e -> e.getUsername().equals(username)).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("list", users);
        // count 是指所有的记录数而非当前页的记录数
        result.put("count", 5);
        return result;
    }
}
