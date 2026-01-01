package Student;

import Utils.DBConnection;

import java.sql.Connection;
import java.util.List;

public class EnrollmentDAO {
    boolean enrollStudent(int studentID, int courseID) {
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
    List<Course> getCoursesByStudent(int studentID){
        try(Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT c.course_id, c.course_name, c.credits " +
                         "FROM courses c " +
                         "JOIN enrollments e ON c.course_id = e.course_id " +
                         "WHERE e.student_id = ?";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentID);
            var rs = pstmt.executeQuery();
            List<Course> courses = new java.util.ArrayList<>();
            while (rs.next()){
                Course course = new Course();
                course.courseID = rs.getInt("course_id");
                course.courseName = rs.getString("course_name");
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
