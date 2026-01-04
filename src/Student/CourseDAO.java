package Student;

import Utils.DBConnection;

import java.sql.Connection;
import java.util.List;

public class CourseDAO {
    public boolean addCourse(Course course) {
        try (Connection conn = DBConnection.getDbConnection()){
            String sql = "INSERT INTO courses (courseName, credits, courseId) VALUES (?, ?, ?)";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, course.getcourseName());
            pstmt.setInt(2, course.getcredits());
            pstmt.setInt(3, course.getcourseID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    boolean updateCourse(Course course){
        try(Connection conn = DBConnection.getDbConnection()){
            String sql = "UPDATE courses SET courseName = ?, credits = ? WHERE courseId = ?";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, course.getcourseName());
            pstmt.setInt(2, course.getcredits());
            pstmt.setInt(3, course.getcourseID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    boolean deleteCourse(int courseID){
        try(Connection conn = DBConnection.getDbConnection()){
            String sql = "DELETE FROM courses WHERE courseId = ?";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, courseID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Course> getAllCourses(){
        try(Connection conn= DBConnection.getDbConnection()){
            String sql = "SELECT * FROM courses";
            var pstmt = conn.prepareStatement(sql);
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
