package server.db.sqlAPI;

import java.sql.*;

public class SqlSelect extends Sql {
    @FunctionalInterface
    public interface SqlThrowingFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    public static Long selectOneLong(String from, String where, String columnName, Object[] values) throws SQLException {
        return selectOneDataSql(from, where, columnName, values, rs->rs.getLong(columnName));
    }

    public static Double selectOneDouble(String from, String where, String columnName, Object[] values) throws SQLException {
        return selectOneDataSql(from, where, columnName, values, rs->rs.getDouble(columnName));
    }

    public static Date selectOneDate(String from, String where, String columnName, Object[] values) throws SQLException {
        return selectOneDataSql(from, where, columnName, values, rs->rs.getDate(columnName));
    }

    public static Integer selectOneInteger(String from, String where, String columnName, Object[] values) throws SQLException {
        return selectOneDataSql(from, where, columnName, values, rs->rs.getInt(columnName));
    }

    public static Boolean selectOneBoolean(String from, String where, String columnName, Object[] values) throws SQLException {
        return selectOneDataSql(from, where, columnName, values, rs->rs.getBoolean(columnName));
    }

    public static String selectOneString(String from, String where, String columnName, Object[] values) throws SQLException {
        return selectOneDataSql(from, where, columnName, values, rs -> rs.getString(columnName));
    }

    private static <T> T selectOneDataSql(String from, String where, String name, Object[] values, SqlThrowingFunction<ResultSet, T> extractor) throws SQLException {
        String sql = "SELECT " + name + " FROM " + from + " WHERE " + where;
        ResultSet rs = executeQuery(sql, values);
        rs.next();
        return extractor.apply(rs);
    }
}
