package server.db.sqlAPI;

import server.db.DatabaseConnection;

import java.sql.*;

public class SqlInsert extends Sql {
    public static void insertSql(String where, String what, Object[] toInsert) throws SQLException {
        insertSql(where, what, toInsert, false).close();
    }

    public static int insertSqlReturningId(String where, String what, Object[] toInsert, String returningName) throws SQLException {
        try (ResultSet rs = insertSql(where, what, toInsert, true)) {
            if (rs.next()) {
                return rs.getInt(returningName);
            } else {
                throw new SQLException();
            }
        }
    }

    private static ResultSet insertSql(String where, String what, Object[] toInsert, boolean returningId) throws SQLException {
        String sql = "INSERT INTO " + where + " " + what + " VALUES " + createQuestionMarkString(toInsert.length);
        sql += returningId ? " RETURNING id" : "";
        return executeQuery(sql, toInsert);
    }
}
