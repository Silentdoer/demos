import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Entrance {
    public static void main(String[] args){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("cqqswms");
        dataSource.setPassword("wms");
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        try{
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select* from agv_work where jobStatus = ?");
            statement.setString(1, "0");
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colNum = metaData.getColumnCount();
            System.out.println(colNum);
            while(resultSet.next()){
                for(int i=1;i<=colNum;i++){
                    System.out.print(resultSet.getString(i) + "    ");
                }
                System.out.println();
            }
            resultSet.close();
            connection.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
