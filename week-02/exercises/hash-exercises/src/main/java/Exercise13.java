import learn.Student;
import java.util.HashMap;

public class Exercise13 {

    public static void main(String[] args) {
        HashMap<Integer, Student> studentHashMap = new HashMap<>();
        studentHashMap.put(123456789, new Student(123456789, "Chris", "Ward"));
        studentHashMap.put(135791357, new Student(135791357, "John", "Doe"));
        studentHashMap.put(246802468, new Student(246802468, "Jane", "Fonda"));

        System.out.println(studentHashMap.get(246802468));

        studentHashMap.remove(246802468);
        System.out.println();

        for (Student student : studentHashMap.values()) {
            System.out.println(student);
        }

    }

}
