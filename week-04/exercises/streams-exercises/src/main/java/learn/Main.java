package learn;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        StudentDataStore ds = new StudentDataStore();
        List<Student> students = ds.all();

        // 0. Print all students
        // iteration solution
        for (Student student : students) {
            System.out.println(student);
        }
        // stream solution
        students.stream().forEach(System.out::println);

        // 1. Print students from Argentina
        // 2. Print students from Argentina, ordered by GPA
        // 3. Print the 4th - 6th ranked students by GPA from Argentina
        // 4. Is anyone from Maldives?
        // 5. Print students who aren't currently registered for a course.
        // 6. Print students who are registered for the course "Literary Genres".
        // 7. Who has the highest GPA?
        // 8. Print every course students are registered for, including repeats.
        // 9. Print a distinct list of courses students are registered for.
        // 10. Print a distinct list of courses students are registered for, ordered by name.
        // 11. Count registrations per course.
        // 12. How many registrations are not graded (GradeType == AUDIT)?
        // 13. Who has the highest pointPercent in "Sacred Writing"?
        // 14. Create a new type, StudentSummary with fields for Country, Major, and IQ.
        //      Map Students to StudentSummary, then filter by IQ (low, high, or whatever seems fun).
        // 15. What is the average GPA per country (remember, it's random fictional data).
        // 16. What is the maximum GPA per country?
        // 17. Print average IQ per Major ordered by IQ ascending.
    }
}
