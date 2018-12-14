package me.silentdoer.demospringtransactionaspectsupport.api.application.service;

import me.silentdoer.demospringtransactionaspectsupport.api.application.model.po.Student;

import java.util.List;

public interface IStudentService{

    int insert(Student student);

    int insertSelective(Student student);

    int insertList(List<Student> students);

    int update(Student student);
}
