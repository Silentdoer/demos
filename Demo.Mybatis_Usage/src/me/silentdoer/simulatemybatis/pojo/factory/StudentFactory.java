package me.silentdoer.simulatemybatis.pojo.factory;

import me.silentdoer.simulatemybatis.pojo.Student;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-22 17:44
 */
public class StudentFactory {
    public static Student newDefaultStudent(){
        Student result = new Student();
        result.setUid(-1L);
        return result;
    }
}
