package sql;

import java.sql.*;
import java.util.Map;
import java.util.function.Function;

public class db {

    /*general use string that points to the name of the schema*/
    public static final String SCHEMA_PATH = "`CouponMania`";

    /* Use this for any CREATE , UPDATE , ADD or DELETE query's you want to run.
    * Return's true if the query ran successfully and false if it didn't. */
    public static boolean runUpdateQuery(String query, Map<Integer, Object> params) {
        return runQuery(query, params, (statement) -> {
            try {
                return statement.execute();
            } catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
        });
    }
    //Same as above but doesnt take params
    public static boolean runUpdateQuery(String query) {
        return runUpdateQuery(query, null);
    }

    /* Use this to run n sql query if you expect to get back information to work with
    * the information is returned as a ResultSet and might be null */
    public static ResultSet getResultSetQuery(String query, Map<Integer, Object> params) {
        return runQuery(query, params, (statement) -> {
            try {
                return statement.executeQuery();
            } catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
        });
    }
    //Same as above but used with query's that don't take parameters
    public static ResultSet getResultSetQuery(String query) {
        return getResultSetQuery(query, null);
    }


    /* This method receives a sql query as a string,a parameter map that has index as key and an object as a value
    * it also receives a function. the purpose of the method is to prepare a PreparedStatement and then
    * use that PreparedStatement in the function passed to "statementRunMethod".
    * statementRunMethod should be a function that executes a PreparedStatement */
    private static <T> T runQuery(String query, Map<Integer, Object> params, Function<PreparedStatement, T> statementRunMethod) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            //go into statement preparation only if a parameter map has been passed to the function
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }

            //apply the prepared statement to the function passed into the runQuery method
            return statementRunMethod.apply(statement);

        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            return null;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }



    /* Gets a prepared statement and a parameter map,
     inserts the values of the parameter map in the ? of the statement,
     This function does not return a value, it changes the original statement it receives*/
    private static void prepareStatementFromParams(PreparedStatement statement, Map<Integer, Object> params) {
        params.forEach((key, value) -> {
            try {
                if (value instanceof Integer) {
                    statement.setInt(key, (Integer) value);
                } else if (value instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof Date) {
                    statement.setDate(key, (Date) value);
                } else if (value instanceof Double) {
                    statement.setDouble(key, (Double) value);
                } else if (value instanceof Boolean) {
                    statement.setBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    statement.setFloat(key, (Float) value);
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        });
    }
}
