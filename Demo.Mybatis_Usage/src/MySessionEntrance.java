import me.silentdoer.simulatemybatis.core.MySession;
import me.silentdoer.simulatemybatis.pojo.Student;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-19 19:14
 */
public class MySessionEntrance {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, DocumentException, InvocationTargetException, ClassNotFoundException {
        MySession session = new MySession();
        Student result = session.selectOne("me.silentdoer.simulatemybatis.mapping.StudentMapper.getSingleStudent", 2L);
        System.out.println(result);
    }
}
