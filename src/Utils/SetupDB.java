package Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDB {
    private static void createDatabaseAndTable() {
        String dbName = "StudentManagement";
        String createDB = "CREATE DATABASE IF NOT EXISTS " + dbName;
        String useDB = "USE " + dbName;
        String createStudentTable = "CREATE TABLE IF NOT EXISTS student (" +
                "studentId INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "rollNo VARCHAR(20) NOT NULL," +
                "email VARCHAR(100) NOT NULL UNIQUE" +
                ")";
        String createCourseTable = "CREATE TABLE IF NOT EXISTS course (" +
                "courseId INT AUTO_INCREMENT PRIMARY KEY," +
                "courseName VARCHAR(100) NOT NULL," +
                "credits INT NOT NULL" +
                ")";

        try (Connection conn = DBConnection.getDbConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createDB);
            stmt.executeUpdate(useDB);
            stmt.executeUpdate(createStudentTable);
            stmt.executeUpdate(createCourseTable);
            System.out.println("Database and table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createDatabaseAndTable();
    }
}