package server.db.sqlAPI;

import java.sql.SQLException;

public class SqlDelete extends Sql {
    public static void deleteDataSql(String from, String where, String name, Object[] values) throws SQLException {
        String sql = "DELETE " + name + " FROM " + from + " WHERE " + where;
        executeUpdate(sql, values);
    }
}
