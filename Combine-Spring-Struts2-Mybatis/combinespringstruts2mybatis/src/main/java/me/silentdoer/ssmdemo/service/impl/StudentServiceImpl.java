package me.silentdoer.ssmdemo.service.impl;

import me.silentdoer.ssmdemo.dao.StudentDao;
import me.silentdoer.ssmdemo.pojo.Student;
import me.silentdoer.ssmdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 2:43 PM
 */
@Service("studentService")  // need trans to studentService, otherwise will be studentServiceImpl will occur can not get bean by name
public class StudentServiceImpl implements StudentService {
    // field name can diff with it's getter&setter; in structure is use getter&setter name.
    private StudentDao studentDao;

    @Autowired
    @Qualifier("studentDao")
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student getOneUserWithLogic(Long uid) {
        // some logic impl
        if(uid == null){
            throw new IllegalArgumentException("uid is not null, please check.");
        }
        System.out.println(String.format("uid is:%s", uid));
        Student student = this.studentDao.selectOne(uid);
        return student;
    }
}
