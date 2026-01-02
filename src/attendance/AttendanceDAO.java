package attendance;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private static final String TABLE_NAME = "StudentManagement.attendance";

    public boolean markAttendance(Attendance attendance) {
        if (attendanceExists(attendance.getStudentId(), attendance.getCourseId(), attendance.getDate())) {
            return false;
        }

        String query = "INSERT INTO " + TABLE_NAME + " (studentId, courseId, date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setInt(1, attendance.getStudentId());
            prepStmt.setInt(2, attendance.getCourseId());
            prepStmt.setDate(3, Date.valueOf(attendance.getDate()));
            prepStmt.setString(4, attendance.getStatus());
            return prepStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to mark attendance", e);
        }
    }

    public boolean attendanceExists(int studentId, int courseId, LocalDate date) {
        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE studentId = ? AND courseId = ? AND date = ? LIMIT 1";
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setInt(1, studentId);
            prepStmt.setInt(2, courseId);
            prepStmt.setDate(3, Date.valueOf(date));
            try (ResultSet result = prepStmt.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to verify attendance record", e);
        }
    }

    public List<Attendance> getAttendanceByStudent(int studentId) {
        String query = "SELECT attendanceId, studentId, courseId, date, status FROM " + TABLE_NAME + " WHERE studentId = ? ORDER BY date";
        List<Attendance> records = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setInt(1, studentId);
            try (ResultSet result = prepStmt.executeQuery()) {
                while (result.next()) {
                    records.add(mapRow(result));
                }
            }
            return records;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch attendance by student", e);
        }
    }

    public List<Attendance> getAttendanceByCourse(int courseId) {
        String query = "SELECT attendanceId, studentId, courseId, date, status FROM " + TABLE_NAME + " WHERE courseId = ? ORDER BY date";
        List<Attendance> records = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setInt(1, courseId);
            try (ResultSet result = prepStmt.executeQuery()) {
                while (result.next()) {
                    records.add(mapRow(result));
                }
            }
            return records;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch attendance by course", e);
        }
    }

    public double calculateAttendancePercentage(int studentId, int courseId) {
        String query = "SELECT "
                + "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS presentCount, "
                + "COUNT(*) AS totalCount "
                + "FROM " + TABLE_NAME + " WHERE studentId = ? AND courseId = ?";
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setInt(1, studentId);
            prepStmt.setInt(2, courseId);
            try (ResultSet result = prepStmt.executeQuery()) {
                if (result.next()) {
                    int total = result.getInt("totalCount");
                    if (total == 0) {
                        return 0.0;
                    }
                    int present = result.getInt("presentCount");
                    return (present * 100.0) / total;
                }
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to calculate attendance percentage", e);
        }
    }

    private Attendance mapRow(ResultSet result) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(result.getInt("attendanceId"));
        attendance.setStudentId(result.getInt("studentId"));
        attendance.setCourseId(result.getInt("courseId"));
        attendance.setDate(result.getDate("date").toLocalDate());
        attendance.setStatus(result.getString("status"));
        return attendance;
    }
}
