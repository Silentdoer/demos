package me.silentdoer.demospringtransactionaspectsupport.api.application.service.impl;

import me.silentdoer.demospringtransactionaspectsupport.api.application.dao.StudentMapper;
import me.silentdoer.demospringtransactionaspectsupport.api.application.model.po.Student;
import me.silentdoer.demospringtransactionaspectsupport.api.application.service.IStudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    @Transactional
    @Override
    public int insertSelective(Student student) {
        int i = studentMapper.insertSelective(student);
        try {
            if (i > 0) {
                // 如果没有@Transactional这里抛出异常也不会回滚，哪怕有TransactionAspectSupport...【因为如果没有@Transactional这里连事务都不会开启】
                throw new IllegalStateException("FFFF");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return i + 2;
    }

    @Override
    public int insertList(List<Student> students) {
        return studentMapper.insertList(students);
    }

    @Override
    public int update(Student student) {
        return studentMapper.update(student);
    }
}
