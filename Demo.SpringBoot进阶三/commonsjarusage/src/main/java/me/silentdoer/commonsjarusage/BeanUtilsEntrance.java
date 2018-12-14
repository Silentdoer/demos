package me.silentdoer.commonsjarusage;

import me.silentdoer.commonsjarusage.model.Student;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import org.apache.commons.beanutils.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class BeanUtilsEntrance {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", 888L);
        properties.put("name", "中文");
        Student student = new Student();
        BeanUtils.populate(student, properties);
        Map<String, String> describe = BeanUtils.describe(student);
        describe.remove("class");
        System.out.println(describe);
        System.out.println(student.getName());

        BeanMap map = new BeanMap(student);
        System.out.println(map);

        Map<String, String> stud = new HashMap<>();
        stud = BeanUtilsBean2.getInstance().describe(student);
        System.out.println(stud.toString());
        /*Map<String, Object> properties = new HashMap<>();
        Student student = new Student();
        student.setFId(388L).setFName("双方都");
        BeanUtils.populate(student, properties);
        System.out.println(properties.get("fId"));*/

        //Student student1 = new Student().setGender("男");
        //System.out.println(BeanUtils.getProperty(student1, "gender"));

        //PropertyUtils.

        //BeanCopier.create()
    }
}
