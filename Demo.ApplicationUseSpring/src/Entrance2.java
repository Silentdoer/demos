//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import silentdoer.demo.spring.pojo.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Enumeration;

// core,context,context-support,beans,commons-logging(aop,tx)
// 测？？？：不用加lib，在classes同级目录加lib目录然后将包放在这个目录
// ，然后以eclipse编译看会是怎样？？？（不行，lib是web里WebappClassLoader里设置了这个路径）
public class Entrance2 {
    public static void main(String[] args) throws Exception
    {
        // 如果applicationContext在项目根目录则不需要src/
        // 有个refresh方法是真正加载/刷新bean的
        ApplicationContext ctx = new FileSystemXmlApplicationContext(Thread.currentThread().getContextClassLoader().getResource("applicationContext.xml").getPath());

        Student student = (Student) ctx.getBean("stud1");
        Constructor constructor = Student.class.getConstructor(new Class[]{long.class, String.class});

        System.out.println(constructor.newInstance(1L, "mm"));
        System.out.println(constructor.getParameterCount());
        System.out.println(constructor.getParameters().length);
        System.out.println(constructor.getParameters()[0].getName());
        System.out.println("MMMMM" + Student.class.getConstructors()[1].getParameters()[0].getName());
        System.out.println(student.getClass());
        System.out.println(student.getName());
        student = ((Student) ctx.getBean("stud2"));
        System.out.println(student.getName());
        System.out.println(student.getUid());
        System.out.println(student.getGender());
        /*for(Enumeration<Object> keys = System.getProperties().keys();keys.hasMoreElements();)
            System.out.println(keys.nextElement());
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("user.dir") + "###" + System.getProperty("user.home"));*/
    }
}
