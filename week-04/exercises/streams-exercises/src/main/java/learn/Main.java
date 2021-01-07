package learn;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class Main {

    public static void main(String[] args) throws IOException {

        StudentDataStore ds = new StudentDataStore();
        List<Student> students = ds.all();

        // 0. Print all students
        // iteration solution
//        for (Student student : students) {
//            System.out.println(student);
//        }

        // stream solution
//        students.stream().forEach(System.out::println);

        printHeader("1. Print students from Argentina");
            students.stream()
                    .filter(s -> s.getCountry().equalsIgnoreCase("Argentina"))
                    .forEach(System.out::println);

        printHeader("2. Print students whose last names starts with 'T'.");
            students.stream()
                    .filter(s -> s.getLastName().toLowerCase().charAt(0) == 't')
                    .forEach(System.out::println);

        printHeader("3. Print students from Argentina, ordered by GPA");
            students.stream()
                    .filter(s -> s.getCountry().equalsIgnoreCase("Argentina"))
                    .sorted(Comparator.comparing(Student::getGpa))
                    .forEach(System.out::println);

        printHeader("4. Print the bottom 10% (100 students) ranked by GPA.");
            students.stream()
                    .sorted(Comparator.comparing(Student::getGpa))
                    .limit(students.size() / 10)
                    .forEach(System.out::println);

        printHeader("5. Print the 4th - 6th ranked students by GPA from Argentina");
            students.stream()
                    .filter(s -> s.getCountry().equalsIgnoreCase("Argentina"))
                    .sorted(Comparator.comparing(Student::getGpa).reversed())
                    .skip(3)
                    .limit(3)
                    .forEach(System.out::println);

        printHeader("6. Is anyone from Maldives?");
            boolean anyoneFromMaldives = students.stream()
                .anyMatch(s -> s.getCountry().equalsIgnoreCase("Maldives"));
            System.out.println(anyoneFromMaldives ? "Yes" : "No");

        printHeader("7. Does everyone have a non-null, non-empty email address?");
            boolean everyoneHasEmail = students.stream()
                    .allMatch(s -> s.getEmailAddress() != null && !s.getEmailAddress().isBlank());
        System.out.println(everyoneHasEmail ? "Yes" : "No");

        printHeader("8. Print students who are currently registered for 5 courses.");
            students.stream()
                    .filter(s -> s.getRegistrations().size() == 5)
                    .forEach(System.out::println);

        printHeader("9. Print students who are registered for the course \"Literary Genres\".");
            students.stream()
                    .filter(s -> s.getRegistrations().stream()
                        .anyMatch(r -> r.getCourse().equalsIgnoreCase("Literary Genres")))
                    .forEach(System.out::println);

        printHeader("10. Who has the latest birthday? Who is the youngest?");
            students.stream()
                    .sorted(Comparator.comparing(Student::getBirthDate).reversed())
                    .limit(1)
                    .forEach(System.out::println);

        printHeader("11. Who has the highest GPA? There may be a tie.");
            System.out.println("Highest GPA: " +
                    students.stream()
                            .max(Comparator.comparing(Student::getGpa)));

        printHeader("12. Print every course students are registered for, including repeats.");
            students.stream()
                    .flatMap(s -> s.getRegistrations().stream())
                    .map(Registration::getCourse)
                    .forEach(System.out::println);

        printHeader("13. Print a distinct list of courses students are registered for.");
            students.stream()
                    .flatMap(s -> s.getRegistrations().stream())
                    .map(Registration::getCourse)
                    .distinct()
                    .forEach(System.out::println);

        printHeader("14. Print a distinct list of courses students are registered for, ordered by name.");
            students.stream()
                    .flatMap(s -> s.getRegistrations().stream())
                    .map(Registration::getCourse)
                    .distinct()
                    .sorted()
                    .forEach(System.out::println);

        printHeader("15. Count students per country.");
            Map<String, Long> studentsPerCountry = students.stream()
                    .collect(Collectors.groupingBy(Student::getCountry, Collectors.counting()));
            for (String country : studentsPerCountry.keySet()) {
                System.out.println(country + ": " + studentsPerCountry.get(country));
            }

        printHeader("16. Count students per country. Order by most to fewest students.");
            studentsPerCountry.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                            .thenComparing(Map.Entry.comparingByKey()))
                    .forEach(System.out::println);

        printHeader("17. Count registrations per course.");
            Map<String, Long> registrationsPerCourse = students.stream()
                    .flatMap(s -> s.getRegistrations().stream())
                    .collect(Collectors.groupingBy(Registration::getCourse, Collectors.counting()));
            for (String course : registrationsPerCourse.keySet()) {
                System.out.println(course + ": " + registrationsPerCourse.get(course));
            }

        printHeader("18. How many registrations are not graded (GradeType == AUDIT)?");
            long registrationsNotGraded = students.stream()
                    .flatMap(s -> s.getRegistrations().stream())
                    .map(Registration::getGradType)
                    .filter(g -> g.equals(GradeType.AUDIT))
                    .count();
            System.out.println("# registrations not graded: " + registrationsNotGraded);

        // Create a new type, StudentSummary with fields for Country, Major, and IQ.
        printHeader("19. Map Students to StudentSummary, then sort and limit by IQ (your choice of low or high");
            students.stream()
                    .map(s -> new StudentSummary(s.getCountry(), s.getMajor(), s.getIq()))
                    .sorted(Comparator.comparing(StudentSummary::getIq).reversed())
                    .limit(10)
                    .forEach(System.out::println);

        printHeader("20. What is the average GPA per country (remember, it's random fictional data).");
            DecimalFormat df = new DecimalFormat("#.00");

            Map<String, Double> averageGPA = students.stream()
                    .collect(Collectors.groupingBy(Student::getCountry, Collectors.averagingDouble(s -> s.getGpa().doubleValue())));

            for (String country : averageGPA.keySet()) {
                System.out.printf("%s's Average GPA: %s%n", country, df.format(averageGPA.get(country)));
            }

        printHeader("21. What is the maximum GPA per country?");

            Map<String, Optional<Student>> maxGPA = students.stream()
                    .collect(Collectors.groupingBy(Student::getCountry, Collectors.maxBy(Comparator.comparing(Student::getGpa))));

            for (String country: maxGPA.keySet()) {
                System.out.printf("%s's Highest GPA: %s%n", country, df.format(maxGPA.get(country).get().getGpa()));
            }

        printHeader("22. Print average IQ per Major ordered by IQ ascending.");
            Map<String, Double> averageIQ = students.stream()
                    .collect(Collectors.groupingBy(Student::getMajor, Collectors.averagingDouble(Student::getIq)));

            averageIQ = averageIQ.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

            for (String major : averageIQ.keySet()) {
                System.out.printf("%s's Average IQ: %S%n", major, df.format(averageIQ.get(major)));
            }

        // STRETCH GOAL!
        printHeader("23. Who has the highest pointPercent in \"Sacred Writing\"?");
            students.stream()
                    .filter(s -> s.getRegistrations().stream()
                            .anyMatch(r -> r.getCourse().equalsIgnoreCase("Sacred Writing")))
                    .sorted(Comparator.comparingDouble((Student s) -> s.getRegistrations().stream()
                            .filter(r -> r.getCourse().equalsIgnoreCase("Sacred Writing"))
                            .findAny().get().getPointPercent()).reversed())
//                    .limit(1)
                    .forEach(s -> System.out.printf("%s %s: %s%n", s.getFirstName(), s.getLastName(), s.getRegistrations().stream()
                            .filter(r -> r.getCourse().equalsIgnoreCase("Sacred Writing"))
                            .findAny().get().getPointPercent())
                    );
    }

    public static Optional<Double> getPointPercent(Student student) {
        return student.getRegistrations().stream()
                .filter(r -> r.getCourse().equalsIgnoreCase("Sacred Writing"))
                .map(Registration::getPointPercent)
                .reduce(Double::sum);
    }

    public static void printHeader(String message) {
        System.out.println();
        System.out.println("=".repeat(message.length()));
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }
}
