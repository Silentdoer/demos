import me.silentdoer.simulatespring.beans.factory.XmlBeanFactory;
import me.silentdoer.simulatespring.pojo.Student;

import java.io.InputStream;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-19 20:01
 */
public class Entrance {
    public static void main(String[] args){
        InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("beans.xml");
        System.out.println(resource == null);
        XmlBeanFactory factory = new XmlBeanFactory(resource);
        String str1 = factory.getBean("str1");
        System.out.println(str1);
        Student student = factory.getBean("stud1");
        System.out.println(student);
        Student student2 = factory.getBean("stud2");
        System.out.println(student2);
    }
}
