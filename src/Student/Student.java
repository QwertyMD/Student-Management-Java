package Student;

public class Student {
    int studentId;
    String name;
    String rollNo;
    String email;

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getEmail() {
        return email;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
