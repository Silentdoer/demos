package me.silentdoer.mybatisinterceptorusage.service;

import me.silentdoer.mybatisinterceptorusage.model.StudentDo;

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
