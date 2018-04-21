import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import silentdoer.demo.mybatis.pojo.Student;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class Entrance {
    private boolean test;

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public static void main(String[] args) throws IOException {
        //mybatis的配置文件
        String resource = "mybatis.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂，通过mybatis.xml构建出一个SqlSessionFactory对象
        // 注意，Mybatis是可以配置多个environment的（比如可能访问多种数据库那么可以为每种数据库配置一个environment）
        // 如果不写environment则用environments的default的值
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is, "mysql_druid");
        System.out.println(sessionFactory.getConfiguration().getEnvironment().getDataSource().getClass());
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession，这里没有设置autoCommit则默认是false，即执行了删除之类的需要调用session.commit();
        SqlSession session = sessionFactory.openSession(false);
        //System.out.println(session.getMapper(StudentMapper.class));
        //PooledDataSourceFactory;
        //UnpooledDataSourceFactory;
        //System.out.println(session.getConfiguration().getEnvironment().getDataSource().getClass());
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "silentdoer.demo.mybatis.mapping.StudentMapper.getStudent";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql，注意1是int型，它不会自动转换为Long，故请用1L
        Student stu = session.selectOne(statement, 1L);
        //SqlSessionManager.newInstance(null, "mysql_druid").openSession();
        //SqlSessionManager.newInstance(null).getMapper()
        // newInstance时就会通过SqlSessionFactoryBuilder创建SqlSessionFactory对象，并且创建一个Proxy对象来执行select之类的
        // 而这个Proxy对象里代理着session对象，这个session对象又是通过SqlSessionFactory对象来open而来。
        //SqlSessionManager.newInstance(null).selectList()
        // 是否需要强制提交
        session.commit(true);
        System.out.println(stu);

        //Configuration config = new XMLConfigBuilder(Resources.getResourceAsStream("mybatis.xml"), null, null).parse();
        //SqlSessionFactory factory = new DefaultSqlSessionFactory(config);
    }
}
