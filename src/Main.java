//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import MarksGrade.*;
import Utils.DBConnection;

import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
            
            try {
                // 1. Get DB connection
                Connection conn = DBConnection.getDbConnection();

                // 2. DAO & Service
                MarksDAO marksDAO = new MarksDAO(conn);
                GradeService gradeService = new GradeService();

                // 3. Create Marks object
                Marks marks = new Marks(0, 1, 1, 78, null);

                // 4. Assign grade
                gradeService.assignGrade(marks);

                // 5. Save marks
                boolean success = marksDAO.addMarks(marks);

                if (success) {
                    System.out.println("Marks added successfully. Grade: " + marks.getGrade());
                } else {
                    System.out.println("Failed to add marks.");
                }

            } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
