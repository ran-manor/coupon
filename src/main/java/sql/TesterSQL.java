package sql;

import java.sql.SQLException;

public class TesterSQL {
    public static void main(String[] args) {
        try {
            DataBaseManager.createDataBase();
            DataBaseManager.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
