package server.db.sqlAPI;

import server.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sql {
    protected static void setParameters(PreparedStatement statement, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof String) {
                statement.setString(i + 1, (String) parameters[i]);
            } else if (parameters[i] instanceof Integer) {
                statement.setInt(i + 1, (Integer) parameters[i]);
            } else if (parameters[i] instanceof Long) {
                statement.setLong(i + 1, (Long) parameters[i]);
            } else if (parameters[i] instanceof Double) {
                statement.setDouble(i + 1, (Double) parameters[i]);
            } else if (parameters[i] instanceof Float) {
                statement.setFloat(i + 1, (Float) parameters[i]);
            } else if (parameters[i] instanceof java.sql.Date) {
                statement.setDate(i + 1, (java.sql.Date) parameters[i]);
            } else if (parameters[i] instanceof java.sql.Timestamp) {
                statement.setTimestamp(i + 1, (java.sql.Timestamp) parameters[i]);
            } else {
                throw new SQLException("Unsupported parameter type: " + parameters[i].getClass().getName());
            }
        }
    }

    public static ResultSet executeQuery(String sqlQuery) throws SQLException {
        return executeQuery(sqlQuery, new Object[]{});
    }

    public static ResultSet executeQuery(String sqlQuery, Object[] parameters) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        setParameters(ps, parameters);
        return ps.executeQuery();
    }

    public static void executeUpdate(String sqlQuery) throws SQLException {
        executeUpdate(sqlQuery, new Object[]{});
    }

    public static void executeUpdate(String sqlQuery, Object[] parameters) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        setParameters(stmt, parameters);
        stmt.executeUpdate();
    }

    protected static String createQuestionMarkString(int length) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < length; i++) {
            sb.append("?, ");
        }
        return sb.delete(sb.length() - 2, sb.length()).append(")").toString();
    }
}
