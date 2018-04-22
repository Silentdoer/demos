package me.silentdoer.ssmdemo.dao.impl;

import me.silentdoer.ssmdemo.dao.StudentDao;
import me.silentdoer.ssmdemo.po.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 10:24 PM
 */
// TODO below two statement will load spring context;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class StudentDaoImplTest {

    @Resource
    private StudentDao studentDao;

    @Test
    public void selectOne() throws Exception {
        Student student = studentDao.selectOne(1);
        System.out.println(student);
    }

}