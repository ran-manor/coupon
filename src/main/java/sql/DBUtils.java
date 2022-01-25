package sql;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.*;
import java.util.Map;

public class DBUtils {
    public static final String SCHEMA_PATH = "`CouponMania`";

    public static void runQuery(String query, Map<Integer, Object> params) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            statement.execute();

        } catch (InterruptedException | SQLException err) {
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static void runQuery(String sql) {
        runQuery(sql, null);
    }

    //TODO: what is the purpose of this?
    //TODO: merge this with runQuery
    public static boolean runQueryGetId(String query, Map<Integer, Object> params ) {
        //bool isUsinggetid
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            //TODO: Merge using this implementtion (or with lambda functions)
            /*
            * if (usinggetid)){
            * PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            * }
            * else (
            * PreparedStatement statement = connection.prepareStatement(query);
            * )
            *
            *  */
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                //TODO: change to custom exception
                throw new SQLException("Creation failed , no id  affected");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
//                   customer.setId(generatedKeys.getLong(1));
                    return true;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    public static ResultSet runQueryForResultSet(String query, Map<Integer, Object> params) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            resultSet = statement.executeQuery();

        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

    public static ResultSet runQueryForResult(String query) {
        return runQueryForResultSet(query, null);
    }

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

