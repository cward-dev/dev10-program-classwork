import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<Integer> listOne = new ArrayList<>();
        Collections.addAll(listOne,1,2,3,8,9);

        List<Integer> listTwo = new ArrayList<>();
        Collections.addAll(listTwo,4,5,6,7);

        List<Integer> newList = combineLists(listOne, listTwo, 3);
        System.out.println(newList);
    }

    public static List<Integer> combineLists(List<Integer> listOne, List<Integer> listTwo, int index) {
        printHeader("newList");

        List<Integer> newList = new ArrayList<>(listOne);
        newList.addAll(index, listTwo);

        return newList;
    }

    public static void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }
}