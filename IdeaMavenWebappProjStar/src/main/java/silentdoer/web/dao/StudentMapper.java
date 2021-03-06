package silentdoer.web.dao;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import silentdoer.web.entity.Student;

import java.util.List;

// Repository的Target类型是ElementType.Type，它可应用在类、接口、枚举等上面
@Repository("studentMapper")
public interface StudentMapper {
    int deleteByPrimaryKey(Long stdId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long stdId);

    List<Student> forTest3(RowBounds pageBounds);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}