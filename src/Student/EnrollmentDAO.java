package Student;

import Utils.DBConnection;

import java.sql.Connection;
import java.util.List;

public class EnrollmentDAO {
    public boolean enrollStudent(int studentID, int courseID) {
        try(Connection conn = DBConnection.getDbConnection()) {
            String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, courseID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    boolean removeEnrollment(int studentID, int courseID){
        try(Connection conn = DBConnection.getDbConnection()) {
            String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, courseID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public List<Course> getCoursesByStudent(int studentID){
        try(Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT c.courseId, c.courseName, c.credits " +
                         "FROM courses c " +
                         "JOIN enrollments e ON c.courseId = e.course_id " +
                         "WHERE e.student_id = ?";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
            var rs = pstmt.executeQuery();
            List<Course> courses = new java.util.ArrayList<>();
            while (rs.next()){
                Course course = new Course();
                course.courseID = rs.getInt("courseId");
                course.courseName = rs.getString("courseName");
                course.credits = rs.getInt("credits");
                courses.add(course);
            }
            return courses;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
