package sql;


import java.sql.*;
import java.util.Map;
import java.util.function.Function;

/**
 * this class holds all the methods that send queries to the sql server
 */
public class DBUtils {
    /**
     * field the holds the schema path in the sql server.
     */
    public static final String SCHEMA_PATH = "`CouponMania`";

    /**
     * a method that sends the query to the runQuery method that accepts map as null.
     * @param query the query (before params insertion) that will be passed to sql database
     * @return the return object of runQuery
     */
    public static boolean runQuery(String query) {
        return runQuery(query, null);
    }

    /**
     * take a query and params to insert into it, defines the Function that will be applied on the PreparedStatement made by the query and params.
     (executes PreparedStatement.execute)
     * @param query the query (before params insertion) that will be passed to sql database
     * @param params the params that will be inserted to the query
     * @return boolean- whether the process was successful.
     */
    public static boolean runQuery(String query, Map<Integer, Object> params){
        return (boolean) runQueryProcess(query , params , statement -> {
            try {
                return statement.execute();
            } catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
        });
    }
    /**
     * take a query and params to insert into it,
     * passes to the other runQueryForResultSet() the query and a null params map.
     * @param query the query (before params insertion) that will be passed to sql database
     * @return boolean- whether the process was successful.
     */
    public static ResultSet runQueryForResultSet(String query) {
        return runQueryForResultSet(query, null);
    }
    /**
     * take a query and params to insert into it, defines the Function that will be applied on the PreparedStatement made by the query and params.
     (executes PreparedStatement.executeQuery)
     * @param query the query (before params insertion) that will be passed to sql database
     * @param params the params that will be inserted to the query
     * @return boolean- whether the process was successful.
     */
    public static ResultSet runQueryForResultSet(String query, Map<Integer, Object> params){
        return  (ResultSet) runQueryProcess(query, params, statement -> {
            try {
                return statement.executeQuery();
            } catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
        });
    }

    /**
     * handles the connection to mysql
     * makes PreparedStatement using the query and the params map.
     * applies a function on the statement using that connection.
     after thus, returns connection to the stack.
     * @param query string sql query, un-parsed.
     * @param params map of params to be inserted to the statement.
     * @param function function to be applied to the final statement.
     * @return (Object) Result from sendig the query. (ResultSet / boolean)
     */
    private static Object runQueryProcess(String query, Map<Integer, Object> params,Function<PreparedStatement , Object> function){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement statement = connection.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statement = connection.prepareStatement(query);

            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
//            resultSet = statement.executeQuery();
            return function.apply(statement );

        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }
//    public static boolean runQueryGetId(String query, Map<Integer, Object> params) {
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//
////            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            PreparedStatement statement = connection.prepareStatement(query);
//            if (params != null) {
//                prepareStatementFromParams(statement, params);
//            }
////            int affectedRows = statement.executeUpdate();
//            statement.executeUpdate();
//            return true;
////            if (affectedRows == 0) {
////                throw new SQLException("Creation failed , no id  affected");
////            }
////            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
////                if (generatedKeys.next()) {
//////                   customer.setId(generatedKeys.getLong(1));
////                    return true;
////                } else {
////                    throw new SQLException("Creating user failed, no ID obtained.");
////                }
////            }
//
//        } catch (InterruptedException | SQLException err) {
//            System.out.println(err.getMessage());
//        } finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//        return false;
//    }

    /**
     * gets an un-parsed PreparedStatement and inserts into it params from map.
     * @param statement the un-parsed PreparedStatement.
     * @param params the params map.
     */
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
                } else if (value instanceof Long) {
                    statement.setLong(key, (Long) value);
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        });
    }

}

