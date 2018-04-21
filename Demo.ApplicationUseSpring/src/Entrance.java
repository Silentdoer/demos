import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import silentdoer.demo.spring.pojo.Student;

// 如果只是产生bean的话只需core和beans及commons-logging
public class Entrance {
    public static void main(String[] args)
    {
        ClassPathResource cpr = new ClassPathResource("applicationContext.xml");

        XmlBeanFactory factory = new XmlBeanFactory(cpr);

        Student student = (Student) factory.getBean("stud1");
        System.out.println(student.getClass());
        System.out.println(student.getName());
    }
}
