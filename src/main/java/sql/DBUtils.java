package sql;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.*;
import java.util.Map;
import java.util.function.Function;

public class DBUtils {
    public static final String SCHEMA_PATH = "`CouponMania`";

    public static void runQuery(String sql) {
        runQuery(sql, null);
    }
    public static void runQuery(String query, Map<Integer, Object> params){
        runQueryProcess(query , params , statement -> {
            try {
                return statement.execute();
            } catch (SQLException err) {
                System.out.println(err.getMessage());
                return null;
            }
        });
    }

    public static ResultSet runQueryForResultSet(String query) {
        return runQueryForResultSet(query, null);
    }
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

    private static Object runQueryProcess(String query, Map<Integer, Object> params,Function<PreparedStatement , Object> function){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
//            resultSet = statement.executeQuery();
            return function.apply(statement);

        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    //TODO: what is the purpose of this?
    public static boolean runQueryGetId(String query, Map<Integer, Object> params) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
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

    //todo: solve prorblem - dbdao nullpointerexception
}

