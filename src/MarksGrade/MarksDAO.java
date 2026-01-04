package MarksGrade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {
    private Connection connection;

    public MarksDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addMarks(Marks marks) {
        String sql = "INSERT INTO marks(studentId, courseId, marks, grade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, marks.getStudentId());
            ps.setInt(2, marks.getCourseId());
            ps.setDouble(3, marks.getMarks());
            ps.setString(4, marks.getGrade());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMarks(Marks marks) {
        String sql = "UPDATE marks SET marks = ?, grade = ? WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, marks.getMarks());
            ps.setString(2, marks.getGrade());
            ps.setInt(3, marks.getStudentId());
            ps.setInt(4, marks.getCourseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Marks getMarks(int studentId, int courseId) {
        String sql = "SELECT * FROM marks WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Marks(
                        rs.getInt("marksId"),
                        rs.getInt("studentId"),
                        rs.getInt("courseId"),
                        rs.getDouble("marks"),
                        rs.getString("grade")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Marks> getMarksByStudent(int studentId) {
        List<Marks> marksList = new ArrayList<>();
        String sql = "SELECT * FROM marks WHERE studentId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                marksList.add(new Marks(
                        rs.getInt("marksId"),
                        rs.getInt("studentId"),
                        rs.getInt("courseId"),
                        rs.getDouble("marks"),
                        rs.getString("grade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marksList;
    }
}
