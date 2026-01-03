package Student;

import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    boolean addStudent(Student student) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "INSERT INTO student (name, rollNo, email,studentId) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getRollNo());
            pstmt.setString(3, student.getEmail());
            pstmt.setInt(4, student.getStudentId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean updateStudent(Student student) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "UPDATE student SET name = ?, rollNo = ?, email = ? , WHERE studentId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getRollNo());
            pstmt.setString(3, student.getEmail());
            pstmt.setInt(4, student.getStudentId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean deleteStudent(int student) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "DELETE FROM student WHERE studentId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    Student getStudentBYID(int studentID) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT * FROM student WHERE studentId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.studentId = rs.getInt("student_id");
                student.name = rs.getString("name");
                student.rollNo = rs.getString("roll_no");
                student.email = rs.getString("email");
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT * FROM student";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.studentId = rs.getInt("student_id");
                student.name = rs.getString("name");
                student.rollNo = rs.getString("roll_no");
                student.email = rs.getString("email");
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
