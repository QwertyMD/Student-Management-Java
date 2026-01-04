package reports;


import Student.Student;
import Student.StudentDAO;
import Student.Course;
import Student.CourseDAO;
import Student.EnrollmentDAO;
import MarksGrade.Marks;
import MarksGrade.MarksDAO;
import attendance.AttendanceDAO;
import Utils.DBConnection;
import java.sql.Connection;
import java.util.*;
import reports.StudentReport;

public class ReportDAO {

    public List<Student> getDefaulterList(double minAttendance) {
        List<Student> defaulters = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection()) {
            StudentDAO studentDAO = new StudentDAO();
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            List<Student> students = studentDAO.getAllStudents();
            for (Student student : students) {
                List<Course> courses = enrollmentDAO.getCoursesByStudent(student.getStudentId());
                for (Course course : courses) {
                    double attendance = attendanceDAO.calculateAttendancePercentage(student.getStudentId(), course.getcourseID());
                    if (attendance < minAttendance) {
                        defaulters.add(student);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaulters;
    }



    public Map<Course, Double> generateCourseAttendanceReport(int courseId) {
        Map<Course, Double> report = new HashMap<>();
        try (Connection conn = DBConnection.getDbConnection()) {
            CourseDAO courseDAO = new CourseDAO();
            StudentDAO studentDAO = new StudentDAO();
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            List<Student> students = studentDAO.getAllStudents();
            List<Course> courses = courseDAO.getAllCourses();
            for (Course course : courses) {
                if (course.getcourseID() == courseId) {
                    for (Student student : students) {
                        double attendance = attendanceDAO.calculateAttendancePercentage(student.getStudentId(), courseId);
                        report.put(course, attendance);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }


    public StudentReport generateStudentReport(int studentId) {
        try (Connection conn = DBConnection.getDbConnection()) {
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
            MarksDAO marksDAO = new MarksDAO(conn);
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            List<Course> courses = enrollmentDAO.getCoursesByStudent(studentId);
            List<Marks> marks = marksDAO.getMarksByStudent(studentId);
            double totalAttendance = 0.0;
            int count = 0;
            for (Course course : courses) {
                totalAttendance += attendanceDAO.calculateAttendancePercentage(studentId, course.getcourseID());
                count++;
            }
            double avgAttendance = count > 0 ? totalAttendance / count : 0.0;
            return new StudentReport(studentId, courses, marks, avgAttendance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}