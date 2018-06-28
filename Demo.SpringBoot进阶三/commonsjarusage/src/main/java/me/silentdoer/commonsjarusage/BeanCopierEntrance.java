package me.silentdoer.commonsjarusage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class BeanCopierEntrance {
    public static void main(String[] args) {
        Student student = Student.builder().fId(3L).fName("时候").fGender('m').build();
        Student student1 = new Student();
        System.out.println(student.toString());
        User user = User.builder().build();

        // false对应后面的null，注意，这两个对象都必须是初始化过的，不是说user为null copy方法内部会自动创建
        BeanCopier.create(Student.class, User.class, false).copy(student, user, null);
        System.out.println(user.toString());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Student {
        private Long fId;
        private String fName;
        private Character fGender;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class User {
        private String fName;
    }
}
