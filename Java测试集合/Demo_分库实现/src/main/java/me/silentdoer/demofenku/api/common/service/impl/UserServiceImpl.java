package me.silentdoer.demofenku.api.common.service.impl;

import me.silentdoer.demofenku.api.common.db.dao.UserMapper;
import me.silentdoer.demofenku.api.common.db.model.User;
import me.silentdoer.demofenku.api.common.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 这一层是业务层面的服务，如更新手机号后  可能 需要通知 其他系统，但是也可能不要或者通知的系统不统一，这就构成了 动态的业务逻辑
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 12:04 PM
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void insertUser(User u) {
        this.userMapper.insert(u);

        //如果类上面没有@Transactional,方法上也没有，哪怕throw new RuntimeException,数据库也会成功插入数据
        //	throw new RuntimeException("测试插入事务");
    }

    @Override
    public void writeAndRead(User u) {
        //这里走写库，那后面的读也都要走写库
        this.insertUser(u);
        //这是刚刚插入的
        User uu = this.findById(u.getFdId());
        System.out.println("==读写混合测试中的读(刚刚插入的)====id=" + u.getFdId() + ",  user_name=" + uu.getFdUserName());
        //为了测试,3个库中id=1的user_name是不一样的
        User uuu = this.findById(1L);
        System.out.println("==读写混合测试中的读====id=1,  user_name=" + uuu.getFdUserName());
    }

    @Override
    public void readAndWrite(User u) {
        //为了测试,3个库中id=1的user_name是不一样的
        User uu = this.findById(1L);
        System.out.println("==读写混合测试中的读====id=1,user_name=" + uu.getFdUserName());
        this.insertUser(u);
    }

    @Override
    public User findById(Long id) {
        User u = this.userMapper.findById(id);
        return u;
    }
}