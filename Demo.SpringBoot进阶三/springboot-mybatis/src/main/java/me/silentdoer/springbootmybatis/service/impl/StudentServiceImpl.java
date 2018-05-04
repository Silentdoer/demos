package me.silentdoer.springbootmybatis.service.impl;

import me.silentdoer.springbootmybatis.dao.StudentMapper;
import me.silentdoer.springbootmybatis.pojo.Student;
import me.silentdoer.springbootmybatis.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * @author silentdoer
 * @version 1.0
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    /**
     * 为此service方法添加事物管理，默认是readOnly为false，且Propagation是REQUIRED
     */
    @Transactional(readOnly = true)
    @Override
    public Student getSingleStudent(Long uid) {
        if(Objects.isNull(uid) || uid <= 0){
            //throw new UncheckedIOException("")
            throw new IllegalArgumentException(String.format("用于查询的uid不和法，其值为:%s", (uid == null ? "NULL" : uid)));
        }
        return this.studentMapper.selectStudentByPrimaryKey(uid);
    }
}
