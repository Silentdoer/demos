package me.silentdoer.springbootpagehelper.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootpagehelper.dao.StudentMapper;
import me.silentdoer.springbootpagehelper.model.StudentDo;
import me.silentdoer.springbootpagehelper.service.IStudentService;
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
        org.apache.ibatis.plugin.Interceptor a;
        // TODO 注意，如果不需要用到pageInfo里的一些信息，那么下面的方法即可，如果需要用到可以用doSelectPageInfo(...)
        // 这里面原本是通过主动studentMapper.selectRepeatableNameStudent(name)来获取List，但是现在PageHelper能够拦截Mybatis生成的sql并且添加order、limit之类的补充表达式
        // 这里的setOrderBy设置的是order by的后面的表达式而非需要orderBy的字段名，而且这里的pageNum和pageSize都是以先排序以后再取相关数据  而非先取数据然后排序
        List<StudentDo> studentDos = PageHelper.startPage(2, 2).setOrderBy("fd_id desc")
                .doSelectPage(() -> studentMapper.selectRepeatableNameStudent(name));
        return studentDos;
    }
}
