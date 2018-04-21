package me.silentdoer.simulatemybatis.core;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-19 18:47
 */
public class MySession {
    private Set<String> opsSet;

    public void init(){
        opsSet = new HashSet<>(4);
        opsSet.addAll(Arrays.asList("select", "insert", "update", "delete"));
    }

    // me.silentdoer.simulatemybatis.mapping.StudentMapper.getSingleStudent, 2L
    public <T> T selectOne(String statement, Object ... params) throws DocumentException, NoSuchMethodException, ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Object result = null;
        //region 加载Mapper文件，提取SQL语句
        SAXReader saxReader = new SAXReader();
        // 这个Mapper文件应该由一个专门的解析器去解析并缓存起来，这里只是为了了解原理故不做这些优化。
        URL mapperConfig = Thread.currentThread().getContextClassLoader().getResource("me/silentdoer/simulatemybatis/mapping/StudentMapper.xml");
        //System.out.println(mapperConfig == null);
        Document doc = saxReader.read(mapperConfig);
        Element mapper = doc.getRootElement();
        /**获取Mapper的名称空间，如me.silentdoer.simulatemybatis.mapping.StudentMapper*/
        Attribute mapperNs = mapper.attribute("namespace");
        if(!statement.contains(mapperNs.getValue())){
            throw new NoSuchMethodException("没有此Mapper文件");
        }
        String part = statement.substring(statement.lastIndexOf(".") + 1);  // 具体的方法如getSingleStudent
        List<Element> selects = mapper.elements("select");
        //Element ele = mapper.elementByID(part);  // 本质上也是通过attribute实现的，但是这个ID似乎只能大写的（看了下源码应该是的）
        Element partEle = null;
        for(Element ele : selects){
            String id = ele.attributeValue("id");
            if(id.equals(part)){
                partEle = ele;
                break;
            }
        }
        if(partEle == null){
            throw new IllegalArgumentException("Mapper里没有提供对应的方法");
        }
        // java.lang.Long
        String paramType = partEle.attributeValue("parameterType");
        // me.silentdoer.simulatemybatis.pojo.Student
        String resultType = partEle.attributeValue("resultType");  // resultMap只是再做了一个映射，自己使用Mybatis最好为每个pojo都做resultMap的映射
        // select name, gender from student where uid=#{?}
        String sql = partEle.getTextTrim();
        // 判断sql里是否包含参数
        if(sql.contains("#")){  // $这种暂且不考虑
            //select * from student where uid=?
            sql = sql.replaceAll("#\\{.+?}", "?");
        }
        /** 开始真正去操作数据库了 */
        //ClassLoader.getSystemClassLoader().loadClass("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");  // 注册驱动，为了能正确解析MySQL服务传来的数据和正确发送数据给MySQL服务
        // Druid的initialSize可以通过这个方法初始化一个连接并将其存入连接池，隔一定空闲时间要发一些心跳数据给MySQL服务器防止被关闭
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test", "root", "nlwyzpass");
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setObject(1, Class.forName(paramType).cast(params[0]));
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colNum = metaData.getColumnCount();
        result = Class.forName(resultType).newInstance();
        // 这里先不做结果集是否只有一条记录的判断
        resultSet.next();
        for(int i=1;i<=colNum;i++){
            // 假设都是小写字母
            final String name = metaData.getColumnName(i);  // such as uid
            Object nwVal = resultSet.getObject(i);  // 要考虑到这个值可能是null，且比如boolean在数据库里设置为tinyint这里需要自己强制转换一下
            //System.out.println(nwVal);
            // boolean是is和set开头，还是不要通过参数来获取，而是直接获取所有的public方法然后找是否有匹配名字的即可，有则调用且强制转换参数类型
            //Method setter = result.getClass().getMethod("set".concat(name.substring(0, 1).toUpperCase().concat(name.substring(1))), nwVal.getClass());
            //System.out.println(setter.getName());
            Method[] methods = result.getClass().getMethods();
            Stream<Method> methodsStream = Arrays.asList(methods).stream();
            Method setter = methodsStream.filter((e) -> e.getName().equals("set".concat(name.substring(0, 1).toUpperCase().concat(name.substring(1))))).findFirst().get();
            //System.out.println(setter.getName().concat("#").concat(nwVal + ""));
            setter.invoke(result, nwVal);
        }
        //endregion
        return (T)result;
    }
}
