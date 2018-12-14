package me.silentdoer.springbootpagehelper.service;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootpagehelper.dao.StudentMapper;
import me.silentdoer.springbootpagehelper.model.StudentDo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/10/2018 3:43 PM
 */
public interface IStudentService {

    List<StudentDo> getStudentByName(@Nonnull String name);
}
