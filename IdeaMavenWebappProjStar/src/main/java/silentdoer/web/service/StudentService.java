package silentdoer.web.service;

import silentdoer.web.entity.Student;

public interface StudentService {

    int removeByPrimaryKey(Long stdId);

    int add(Student std);

    int addSelective(Student std);

    Student getByPrimaryKey(Long stdId);
}
