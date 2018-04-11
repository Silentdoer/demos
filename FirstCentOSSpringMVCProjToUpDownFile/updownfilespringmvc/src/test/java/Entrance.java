import com.mysql.jdbc.StatementImpl;

import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/9/18 2:27 PM
 */
public class Entrance {
    public static void main(String[] args){
        //com.mysql.jdbc.Connection connection = DriverManager.getConnection("");
        //Statement statement = connection.createStatement();
        //StatementImpl imp;
        //imp.getConnection();  // this is the connection obj
        /**
         * TODO instead of statement.executeQuery(..) we can use :
         * resultSet = conn.execSQL(this, sql, this.maxRows, (Buffer)null, this.resultSetType
         * , this.resultSetConcurrency, this.createStreamingResultSet(), this.currentCatalog, cachedFields);
         */
    }
}
