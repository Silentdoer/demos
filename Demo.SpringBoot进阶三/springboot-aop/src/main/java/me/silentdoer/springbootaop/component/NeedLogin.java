package me.silentdoer.springbootaop.component;

import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootaop.aop.support.BeforeService;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Service
@Slf4j
public class NeedLogin implements BeforeService {
    @Override
    public void before(JoinPoint jp) {
        log.info("没有登录，请先登录");
//        throw new ApiException("未登录");
    }
}
