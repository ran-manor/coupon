package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtils {
    public static final String SCHEMA_PATH = "`CouponMania`";

    public static void runQuery(String query){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(query);

            //run statement
            statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        }
        finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }
}
