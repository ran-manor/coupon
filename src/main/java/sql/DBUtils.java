package sql;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.*;
import java.util.Map;

public class DBUtils {
    public static final String SCHEMA_PATH = "`CouponMania`";
    public static void runQuery(String sql)  {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static void runQuery(String query, Map<Integer, Object> params) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            statement.execute();

        } catch (InterruptedException  | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
    public static boolean runQueryGetId( Object object, String query, Map<Integer, Object> params)  {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
             int affectedRows = statement.executeUpdate();
            if (affectedRows == 0 ){
                throw new SQLException("Creation failed , no id  affected");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                  // customer.setId(generatedKeys.getLong(1));
                    return true;
                }
                else {
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
//
//    public static boolean runQueryGetId(Company user, String query, Map<Integer, Object> params)  {
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
//            if (params != null) {
//                prepareStatementFromParams(statement, params);
//            }
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows == 0 ){
//                throw new SQLException("Creation failed , no id  affected");
//            }
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                  user.setId( generatedKeys.getLong(1));
//                    return true;
//                }
//                else {
//                    throw new SQLException("Creating user failed, no ID obtained.");
//                }
//            }
//
//        } catch (InterruptedException | SQLException err) {
//            System.out.println(err.getMessage());
//        } finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//        return false;
//    }
//
//    public static boolean runQueryGetId(Coupon user, String query, Map<Integer, Object> params)  {
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
//            if (params != null) {
//                prepareStatementFromParams(statement, params);
//            }
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows == 0 ){
//                throw new SQLException("Creation failed , no id  affected");
//            }
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                   user.setId(generatedKeys.getLong(1));
//                    return true;
//                }
//                else {
//                    throw new SQLException("Creating user failed, no ID obtained.");
//                }
//            }
//
//        } catch (InterruptedException | SQLException err) {
//            System.out.println(err.getMessage());
//        } finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//        return false;
//    }

    public static void runQuery(String query, int key, Object value)  {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
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
            statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static ResultSet runQueryForResultSet(String query, Map<Integer, Object> params)  {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            resultSet = statement.executeQuery();

        } catch (InterruptedException |SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

    public static ResultSet runQueryForResultSet(String query, int key, Object value)  {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
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
            resultSet = statement.executeQuery();

        } catch (InterruptedException  | SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

    public static ResultSet runQueryForResult(String query) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (InterruptedException err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
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
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        });
    }
}

