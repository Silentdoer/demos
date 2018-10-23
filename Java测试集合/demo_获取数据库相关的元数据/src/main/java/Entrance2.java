import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 6:58 PM
 */
public class Entrance2 {
    private final static String DRIVER = "com.mysql.jdbc.Driver";

    private final static String URL = "jdbc:mysql://localhost:3306/shiro_demo";

    private final static String USERNAME = "root";

    private final static String PASSWORD = "mnwwyzpass";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return con;
    }

    /**
     * 还是这种方式好，不过这种方式要求数据库用户有比较高的权限（一般用root）
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        String sql = "select table_schema, table_name, column_name, is_nullable, column_type, column_comment from information_schema.columns where table_schema = 'shiro_demo'";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        List<List<String>> records = new LinkedList<>();
        while (resultSet.next()) {
            List<String> record = new LinkedList<>();
            String tableSchema = resultSet.getString(1);
            String tableName = resultSet.getString(2);
            String columnName = resultSet.getString(3);
            String isNullable = resultSet.getString(4);
            String columnType = resultSet.getString(5);
            String columnComment = resultSet.getString(6);
            // 往一条记录里扩充字段
            record.add(tableName);
            record.add(columnName);
            record.add(columnType);
            record.add(isNullable);
            record.add(columnComment);
            record.add(tableSchema);
            records.add(record);
            //System.out.println(String.join(",", tableSchema, tableName, columnName, isNullable, columnType, columnComment));
        }
        // TODO records里存储的是所有表的字段
        Map<String, List<List<String>>> tablesWithColumns = records.stream().collect(Collectors.groupingBy(r -> r.get(0)));
        System.out.printf("共有%s张表，表名分别为：%s%s", tablesWithColumns.size(), String.join(",", tablesWithColumns.keySet()), System.lineSeparator());

        List<String> info = tablesWithColumns.entrySet().stream().map(r -> {
            String tableName = r.getKey();
            String schemaName = r.getValue().get(0).get(5);
            List<List<String>> value = r.getValue();

            List<String> collect = value.stream().map(e -> {
                // TODO remove后size也变了（并发情况下慎用这种操作）
                e.remove(0);
                e.remove(e.size() - 1);
                String join = String.join(",", e);
                return join;
            }).collect(Collectors.toList());
            collect.add(0, tableName);
            collect.add(1, schemaName);
            return String.join("#", collect);
        }).collect(Collectors.toList());
        System.out.println(String.join("$", info));
        System.out.println();
        resultSet.close();
        sql = "select table_schema, table_name, table_comment from information_schema.tables where table_schema = 'shiro_demo'";
        resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            String tableSchema = resultSet.getString(1);
            String tableName = resultSet.getString(2);
            String tableComment = resultSet.getString(3);
            System.out.println(String.join(",", tableSchema, tableName, tableComment));
        }
        resultSet.close();
        connection.close();
    }
}
