package marks;

public class GradeService {

    public String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B+";
        else if (marks >= 60) return "B";
        else if (marks >= 50) return "C";
        else return "F";
    }

    public boolean assignGrade(Marks marks) {
        marks.setGrade(calculateGrade(marks.getMarks()));
        return true;
    }
}
