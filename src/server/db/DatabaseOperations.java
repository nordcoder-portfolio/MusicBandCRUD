package server.db;

import common.entity.*;

import static common.util.LoggingIn.DOES_NOT_EXIST;
import static common.util.LoggingIn.EXISTS;

import common.util.LoggingIn;

import static common.util.Util.mapRowToWorker;
import static server.db.sqlAPI.Sql.executeQuery;
import static server.db.sqlAPI.SqlDelete.deleteDataSql;
import static server.db.sqlAPI.SqlInsert.insertSql;
import static server.db.sqlAPI.SqlInsert.insertSqlReturningId;
import static server.db.sqlAPI.SqlSelect.*;

import java.sql.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


public class DatabaseOperations {
    public static void insertUser(String username, String passwordHash) throws SQLException {
        insertSql("users", "(username, password)", new Object[]{username, passwordHash});
    }

    public static int getUserIdByUsername(String username) throws SQLException {
        return selectOneInteger("users", "username = ?", "id", new Object[]{username});
    }

    public static LoggingIn authenticateUserCheck(String login, String passwordHash) throws SQLException {
        try {
            selectOneInteger("users", "username = ? AND password_hash = ?", "id", new Object[]{login, passwordHash});
            return EXISTS;
        } catch (SQLException e) {
            return DOES_NOT_EXIST;
        }
    }

    public static Set<Worker> getAllWorkersFromDB() throws SQLException {
        Set<Worker> result = Collections.synchronizedSet(new TreeSet<>(Comparator.comparingDouble(Worker::getId)));
        ResultSet resultSet = executeQuery("SELECT * FROM workers");
        while (resultSet.next()) {
            result.add(mapRowToWorker(resultSet));
        }
        return result;
    }

    public static int insertWorkerSql(Worker w, int userId) throws SQLException {
        int coordinatesId = insertSqlReturningId("coordinates", "(x, y)", new Object[]{w.getCoordinates().getX(), w.getCoordinates().getY()}, "id");
        int personId = insertSqlReturningId("persons", "(height, weight, hair_color, nationality)", w.getPerson().getObjects(), "id");
        return insertSqlReturningId("workers", "(name, coordinates_id, salary, end_date, position, status, person_id, user_id)", w.getObjects(coordinatesId, personId, userId), "id");
    }

    public static void deleteWorkerSql(int workerId) throws SQLException {
        int coordsId = selectOneInteger("workers", "id = ?", "coordinates_id", new Object[]{workerId});
        int personId = selectOneInteger("workers", "id = ?", "person_id", new Object[]{workerId});
        deleteDataSql("workers", "id = ?", "", new Object[]{workerId});
        deleteDataSql("coordinates", "id = ?", "", new Object[]{coordsId});
        deleteDataSql("persons", "id = ?", "", new Object[]{personId});

    }

    public static void clearDataBase() throws SQLException {
        Set<Worker> workers = getAllWorkersFromDB();
        for (Worker w : workers) {
            deleteWorkerSql(w.getId());
        }
    }

    public static boolean isSuperuser(int userId) throws SQLException {
        return selectOneBoolean("users", "id = ?", "superuser", new Object[]{userId});
    }

    public static int getUserIdByWorkerId(int id) throws SQLException {
        return selectOneInteger("workers", "id = ?", "user_id", new Object[]{id});
    }

    public static Coordinates getCoordinatesById(int id) throws SQLException {
        Double x = selectOneDouble("coordinates", "id = ?", "x", new Object[]{id});
        Long y = selectOneLong("coordinates", "id = ?", "y", new Object[]{id});
        return new Coordinates(x, y);
    }

    public static Person getPersonById(int personId) throws SQLException {
        Integer height = selectOneInteger("persons", "id = ?", "height", new Object[]{personId});
        Double weight = selectOneDouble("persons", "id = ?", "weight", new Object[]{personId});
        String color = selectOneString("persons", "id = ?", "hair_color", new Object[]{personId});
        Color resColor = color == null ? null : Color.valueOf(color);
        Country nationality = Country.valueOf(selectOneString("persons", "id = ?", "nationality", new Object[]{personId}));
        return new Person(height, weight, resColor, nationality);
    }
}

