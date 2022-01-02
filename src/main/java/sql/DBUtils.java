package sql;

import Beans.Category;

import java.sql.*;
import java.util.Map;

public class DBUtils {

    //TODO: change to functional
    public static final String SCHEMA_PATH = "`CouponMania`";
//    public static void runUpdateQuery(String query) {
//        Connection connection = null;
//
//
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//
//            PreparedStatement statement = connection.prepareStatement(query);
//
//            //run statement
//            statement.execute();
//        } catch (InterruptedException | SQLException err) {
//            System.out.println(err.getMessage());
//        } finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//
//    }
//
//    public static ResultSet getResultSetFromQuery(String query){
//        Connection connection = null;
//    }
//    public static ResultSet getResultSetFromQuery(String query , Map<Integer, Object> params){
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement statement = connection.prepareStatement(query);
//
//             return statement;
//        } catch (SQLException | InterruptedException err) {
////            System.out.println(err.getMessage());
//            err.printStackTrace();
//            return null;
//        } finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//    }

    public static boolean runUpdateQuery(String query , Map<Integer, Object> params){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            prepareStatementFromParams(statement , params);

            return statement.execute();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static ResultSet getResultSetQuery(String query){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            return null;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
    public static ResultSet getResultSetQuery(String query , Map<Integer, Object> params){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(query);
            prepareStatementFromParams(statement , params);
            return statement.executeQuery();
        } catch (InterruptedException | SQLException err) {
            System.out.println(err.getMessage());
            return null;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
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
//                    System.out.println(err.getMessage());
                    err.printStackTrace();
                }
            });
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement statement = connection.prepareStatement(query);
//            params.forEach((key, value) -> {
//                try {
//                    if (value instanceof Integer) {
//                        statement.setInt(key, (Integer) value);
//                    } else if (value instanceof String) {
//                        statement.setString(key, String.valueOf(value));
//                    } else if (value instanceof Date) {
//                        statement.setDate(key, (Date) value);
//                    } else if (value instanceof Double) {
//                        statement.setDouble(key, (Double) value);
//                    } else if (value instanceof Boolean) {
//                        statement.setBoolean(key, (Boolean) value);
//                    } else if (value instanceof Float) {
//                        statement.setFloat(key, (Float) value);
//                    }
//                } catch (SQLException err) {
////                    System.out.println(err.getMessage());
//                    err.printStackTrace();
//                }
//            });
//             return statement;
//        } catch (SQLException | InterruptedException err) {
////            System.out.println(err.getMessage());
//            err.printStackTrace();
//            return null;
//        } finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }


    }



