package Utils;//package Workshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String DB_USER = "aaditya";
    static final String DB_PASSWORD = "admin";
    public static DBConnection instance;

    private DBConnection() {
        if (instance == null) {
            instance = new DBConnection();
        }
    }
    public static Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}