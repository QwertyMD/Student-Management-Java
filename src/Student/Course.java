package Student;

public class Course {
    int courseID;
    String courseName;
    int credits;

    public String getcourseName() {
        return courseName;
    }

    public int getcredits() {
        return  credits;
    }

    public int getcourseID() {
        return courseID;
    }

    public void setcourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setcourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setcredits(int credits) {
        this.credits = credits;
    }
}
