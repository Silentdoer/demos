package me.silentdoer.mybatisinterceptorusage.service.impl;

import me.silentdoer.mybatisinterceptorusage.dao.StudentMapper;
import me.silentdoer.mybatisinterceptorusage.model.StudentDo;
import me.silentdoer.mybatisinterceptorusage.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 3:45 PM
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<StudentDo> getStudentByName(@Nonnull String name) {

        return this.studentMapper.selectRepeatableNameStudent(name);
    }
}
