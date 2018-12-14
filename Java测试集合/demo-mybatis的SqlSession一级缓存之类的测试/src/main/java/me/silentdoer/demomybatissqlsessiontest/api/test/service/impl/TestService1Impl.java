package me.silentdoer.demomybatissqlsessiontest.api.test.service.impl;

import me.silentdoer.demomybatissqlsessiontest.api.test.dao.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/3/2018 5:58 PM
 */
@Service
public class TestService1Impl {

    @Resource
    private StudentMapper studentMapper;

    public static StudentMapper studentMapperStatic;

    public String test11() {
        TestService1Impl.studentMapperStatic = this.studentMapper;
        System.out.println(this.studentMapper.selectById(20L).getFdId());
        return "service1";
    }
}
