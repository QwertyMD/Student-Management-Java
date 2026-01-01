package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    static final String DB_URL = "jdbc:mysql://localhost:3306/?serverTimezone=UTC"; // for setup
    static final String DB_USER = "aaditya";
    static final String DB_PASSWORD = "admin";

    // Singleton instance (optional)
    public static DBConnection instance;

    private DBConnection() {
        if (instance == null) {
            instance = new DBConnection();
        }
    }

    public static Connection getDbConnection() throws SQLException {
        try {
            // Load MySQL driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
