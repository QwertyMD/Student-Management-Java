package attendance;

import java.time.LocalDate;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO;

    public AttendanceService(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    public boolean markPresent(int studentId, int courseId, LocalDate date) {
        Attendance attendance = new Attendance(studentId, courseId, date, "Present");
        return attendanceDAO.markAttendance(attendance);
    }

    public boolean markAbsent(int studentId, int courseId, LocalDate date) {
        Attendance attendance = new Attendance(studentId, courseId, date, "Absent");
        return attendanceDAO.markAttendance(attendance);
    }
}
