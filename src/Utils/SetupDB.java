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
        String createCourseTable = "CREATE TABLE IF NOT EXISTS courses (" +
            "courseId INT AUTO_INCREMENT PRIMARY KEY," +
            "courseName VARCHAR(100) NOT NULL," +
            "credits INT NOT NULL" +
            ")";
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "username VARCHAR(100) NOT NULL UNIQUE," +
            "password VARCHAR(100) NOT NULL" +
            ")";
        String createEnrollmentsTable = "CREATE TABLE IF NOT EXISTS enrollments (" +
            "student_id INT NOT NULL," +
            "course_id INT NOT NULL," +
            "PRIMARY KEY (student_id, course_id)," +
            "FOREIGN KEY (student_id) REFERENCES student(studentId)," +
            "FOREIGN KEY (course_id) REFERENCES courses(courseId)" +
            ")";
        String createAttendanceTable = "CREATE TABLE IF NOT EXISTS attendance (" +
            "attendanceId INT AUTO_INCREMENT PRIMARY KEY," +
            "studentId INT NOT NULL," +
            "courseId INT NOT NULL," +
            "date DATE NOT NULL," +
            "status VARCHAR(10) NOT NULL," +
            "FOREIGN KEY (studentId) REFERENCES student(studentId)," +
            "FOREIGN KEY (courseId) REFERENCES courses(courseId)" +
            ")";
        String createMarksTable = "CREATE TABLE IF NOT EXISTS marks (" +
            "marksId INT AUTO_INCREMENT PRIMARY KEY," +
            "studentId INT NOT NULL," +
            "courseId INT NOT NULL," +
            "marks DOUBLE NOT NULL," +
            "grade VARCHAR(5)," +
            "FOREIGN KEY (studentId) REFERENCES student(studentId)," +
            "FOREIGN KEY (courseId) REFERENCES courses(courseId)" +
            ")";


        try (Connection conn = DBConnection.getDbConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createDB);
            stmt.executeUpdate(useDB);
            stmt.executeUpdate(createStudentTable);
            stmt.executeUpdate(createCourseTable);
            stmt.executeUpdate(createUsersTable);
            stmt.executeUpdate(createEnrollmentsTable);
            stmt.executeUpdate(createAttendanceTable);
            stmt.executeUpdate(createMarksTable);

            System.out.println("Database and table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createDatabaseAndTable();
    }
}