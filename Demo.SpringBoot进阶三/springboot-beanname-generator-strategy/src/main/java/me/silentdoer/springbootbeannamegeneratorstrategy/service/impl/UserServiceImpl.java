package me.silentdoer.springbootbeannamegeneratorstrategy.service.impl;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootbeannamegeneratorstrategy.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/6/2018 3:43 PM
 */
// TODO 默认情况这里会以userServiceImpl作为beanName生成bean，因此需要自定义BeanNameGenerator来去掉后面的Impl使得@Resource更快找到bean
@Service
public class UserServiceImpl implements IUserService {
}
