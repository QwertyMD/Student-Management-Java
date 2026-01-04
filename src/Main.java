//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import MarksGrade.*;
import Utils.DBConnection;

import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
        System.out.println("Student Management System Demo\n");
        // Ensure DB and tables are created
        Utils.SetupDB.main(new String[]{});
        try {
            // 1. Get DB connection
            Connection conn = DBConnection.getDbConnection();

            // --- Authentication Demo ---
            Authentication.UserDAO userDAO = new Authentication.UserDAO();
            Authentication.AuthService authService = new Authentication.AuthService(userDAO);
            Authentication.User newUser = new Authentication.User();
            newUser.username = "student1";
            newUser.password = "pass123";
            userDAO.saveUser(newUser);
            boolean isAuthenticated = authService.authenticate("student1", "pass123");
            System.out.println("Authentication: " + (isAuthenticated ? "Success" : "Failed"));

            // --- Student & Course Management Demo ---
            Student.StudentDAO studentDAO = new Student.StudentDAO();
            Student.Student student = new Student.Student();
            student.setStudentId(1);
            student.setName("Student One");
            student.setRollNo("101");
            student.setEmail("student1@example.com");
            studentDAO.addStudent(student);
            Student.CourseDAO courseDAO = new Student.CourseDAO();
            Student.Course course = new Student.Course();
            course.setcourseID(1);
            course.setcourseName("Mathematics");
            course.setcredits(4);
            courseDAO.addCourse(course);
            Student.EnrollmentDAO enrollmentDAO = new Student.EnrollmentDAO();
            enrollmentDAO.enrollStudent(1, 1);

            // --- Attendance Management Demo ---
            attendance.AttendanceDAO attendanceDAO = new attendance.AttendanceDAO();
            attendance.AttendanceService attendanceService = new attendance.AttendanceService(attendanceDAO);
            java.time.LocalDate today = java.time.LocalDate.now();
            attendanceService.markPresent(1, 1, today);
            double attendancePercent = attendanceDAO.calculateAttendancePercentage(1, 1);
            System.out.println("Attendance Percentage for student 1 in course 1: " + attendancePercent);

            // --- Marks & Grade Management Demo ---
            MarksGrade.MarksDAO marksDAO = new MarksGrade.MarksDAO(conn);
            MarksGrade.GradeService gradeService = new MarksGrade.GradeService();
            MarksGrade.Marks marks = new MarksGrade.Marks(0, 1, 1, 78, null);
            gradeService.assignGrade(marks);
            boolean success = marksDAO.addMarks(marks);
            if (success) {
                System.out.println("Marks added successfully. Grade: " + marks.getGrade());
            } else {
                System.out.println("Failed to add marks.");
            }

            // --- Reports & System Integration Demo ---
            reports.ReportDAO reportDAO = new reports.ReportDAO();
            reports.StudentReport report = reportDAO.generateStudentReport(1);
            if (report != null) {
                System.out.println("\nStudent Report:");
                System.out.println("Student ID: " + report.getStudentId());
                System.out.println("Courses: " + report.getCourses().size());
                System.out.println("Marks count: " + report.getMarks().size());
                for (MarksGrade.Marks m : report.getMarks()) {
                    System.out.println("  Course ID: " + m.getCourseId() + ", Marks: " + m.getMarks() + ", Grade: " + m.getGrade());
                }
                System.out.println("Attendance Percentage: " + report.getAttendancePercentage());
            } else {
                System.out.println("Failed to generate student report.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
