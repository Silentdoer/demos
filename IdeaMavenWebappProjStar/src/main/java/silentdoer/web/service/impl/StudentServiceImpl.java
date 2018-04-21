package silentdoer.web.service.impl;

import org.springframework.stereotype.Service;
import silentdoer.web.dao.StudentMapper;
import silentdoer.web.entity.Student;
import silentdoer.web.service.StudentService;

import javax.annotation.Resource;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;  // IDEA的检查很仔细可能会导致一些没错的却提示错误，像这个可以不必理会

    @Override
    public int removeByPrimaryKey(Long stdId) {
        if(stdId < 1)
            return -1;
        return studentMapper.deleteByPrimaryKey(stdId);
    }

    @Override
    public int add(Student std) {
        return studentMapper.insert(std);
    }

    @Override
    public int addSelective(Student std){
        if(std.getStdId() != null)  // 这里不允许客户端自己定义主键值，因为数据库中已经设定好主键由自增完成
            return -1;
        return studentMapper.insertSelective(std);
    }

    @Override
    public Student getByPrimaryKey(Long stdId) {
        if(stdId < 1)
            return null;
        return studentMapper.selectByPrimaryKey(stdId);
    }
}
