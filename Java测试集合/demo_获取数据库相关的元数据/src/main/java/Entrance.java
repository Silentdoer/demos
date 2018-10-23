import java.sql.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 6:08 PM
 */
public class Entrance {

    private final static String DRIVER = "com.mysql.jdbc.Driver";

    private final static String URL = "jdbc:mysql://localhost:3306/shiro_demo";

    private final static String USERNAME = "root";

    private final static String PASSWORD = "mnwwyzpass";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        just4AllDBInfo();
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return con;
    }

    public static void just4AllDBInfo() throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
        DatabaseMetaData metaDate = con.getMetaData();
        //1.得到数据库下所有数据表，TODO 这里可以理解为select* from information_schema.tables where table_schema = 'shiro_demo';
        ResultSet rs = metaDate.getTables("", "shiro_demo", null, null);
        //2.根据表名  拼接成SQL语句  查询到某个表的所有列
        PreparedStatement prep = null;
        // TODO rs是表集合，这里在遍历所有的表，
        while (rs.next()) {
            // TODO rs.getString(3)获得的是表名（可以参考information_schema.tables表结构）（好吧，经过测试不是这个说法。。）
            //String tableSchema = rs.getString("table_schema");
            String tableName2 = rs.getString("table_name");
            //String engine = rs.getString("engine");  // innodb之类的
            //String tableComment = rs.getString("table_comment");

            //metaDate.getColumns()

            // 由于只需要获取元数据，所以这里用1=2
            String sql = "SELECT* FROM " + tableName2 + " WHERE 1=2;";
            prep = con.prepareStatement(sql);
            ResultSet set = prep.executeQuery(sql);
            ResultSetMetaData data = set.getMetaData();
            //迭代取到所有列信息
            for (int i = 1; i <= data.getColumnCount(); i++) {
                // 获得所有列的数目及实际列数
                int columnCount = data.getColumnCount();
                // TODO 获得指定列的列名，如fd_name
                String columnName = data.getColumnName(i);

                // 获得指定列的列值
                int columnType = data.getColumnType(i);
                // TODO 获得指定列的数据类型名，如VARCHAR/INT之类的（这个只是一级类型后续还有精度）
                String columnTypeName = data.getColumnTypeName(i);
                // TODO 某列类型的精确度(类型的长度)，
                int precision = data.getPrecision(i);
                // 所在的Catalog名字
                String catalogName = data.getCatalogName(i);
                // 对应数据类型的类
                String columnClassName = data.getColumnClassName(i);
                // 在数据库中类型的最大字符个数
                int columnDisplaySize = data.getColumnDisplaySize(i);
                // 默认的列的标题
                String columnLabel = data.getColumnLabel(i);
                // 获得列的模式
                String schemaName = data.getSchemaName(i);

                // 小数点后的位数
                int scale = data.getScale(i);
                // 获取某列对应的表名
                String tableName = data.getTableName(i);
                // 是否自动递增
                boolean isAutoInctement = data.isAutoIncrement(i);
                // 在数据库中是否为货币型
                boolean isCurrency = data.isCurrency(i);
                // 是否为空
                int isNullable = data.isNullable(i);
                // 是否为只读
                boolean isReadOnly = data.isReadOnly(i);
                // 能否出现在where中
                boolean isSearchable = data.isSearchable(i);

                System.out.println("数据表：" + rs.getString(3));
                System.out.println("数据表" + rs.getString(3) + "列总数：" + columnCount);
                System.out.println("获得列" + i + "的字段名称:" + columnName);
                System.out.println("获得列" + i + "的类型,返回SqlType中的编号:" + columnType);
                System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
                System.out.println("获得列" + i + "所在的Catalog名字:" + catalogName);
                System.out.println("获得列" + i + "对应数据类型的类:" + columnClassName);
                System.out.println("获得列" + i + "在数据库中类型的最大字符个数:" + columnDisplaySize);
                System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
                System.out.println("获得列" + i + "的模式:" + schemaName);
                System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
                System.out.println("获得列" + i + "小数点后的位数:" + scale);
                System.out.println("获得列" + i + "对应的表名:" + tableName);
                System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
                System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
                System.out.println("获得列" + i + "是否为空:" + isNullable);
                System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
                System.out.println("获得列" + i + "能否出现在where中:" + isSearchable);

                System.out.println();
            }
        }
    }
}
