package me.silentdoer.demomybatissqlsessiontest.api.test.service.impl;

import me.silentdoer.demomybatissqlsessiontest.api.test.dao.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/3/2018 6:00 PM
 */
@Service
public class TestService2Impl {

    @Resource
    private StudentMapper studentMapper;

    public String test21() {
        TestService1Impl.studentMapperStatic = this.studentMapper;
        System.out.println(this.studentMapper.selectById(22L).getFdId());
        System.err.println(this.studentMapper == TestService1Impl.studentMapperStatic);
        return "service2";
    }
}
