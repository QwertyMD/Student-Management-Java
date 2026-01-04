package reports;

import java.util.List;
import java.util.ArrayList;
import Student.Student;
import Student.Course;

import MarksGrade.Marks;

public class StudentReport {
    private int studentId;
    private List<Course> courses;
    private List<Marks> marks;
    private double attendancePercentage;

    public StudentReport(int studentId, List<Course> courses, List<Marks> marks, double attendancePercentage) {
        this.studentId = studentId;
        this.courses = courses;
        this.marks = marks;
        this.attendancePercentage = attendancePercentage;
    }

    public int getStudentId() {
        return studentId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Marks> getMarks() {
        return marks;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }
}