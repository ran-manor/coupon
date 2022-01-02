package sql;

import java.sql.*;
import java.util.Map;
import java.util.function.Function;

public class DBUtils {
    public static final String SCHEMA_PATH = "`CouponMania`";

    public static boolean runUpdateQuery(String query, Map<Integer, Object> params) {
        return (boolean) runQuery(query, params, (statement) -> {
            try {
                return statement.execute();
            } catch (SQLException err) {
                err.getMessage();
                return null;
            }
        });
    }

    public static boolean runUpdateQuery(String query) {
        return runUpdateQuery(query, null);
    }

    public static ResultSet getResultSetQuery(String query) {
        return getResultSetQuery(query, null);
    }

    public static ResultSet getResultSetQuery(String query, Map<Integer, Object> params) {
        return (ResultSet) runQuery(query, params, (statement) -> {
                    try {
                        return statement.executeQuery();
                    } catch (SQLException err) {
                        err.getMessage();
                        return null;
                    }
                    });
    }


    private static Object runQuery(String query, Map<Integer, Object> params, Function<PreparedStatement, Object> statementAction) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null) {
                prepareStatementFromParams(statement, params);
            }
            return statementAction.apply(statement);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
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
    }
}



