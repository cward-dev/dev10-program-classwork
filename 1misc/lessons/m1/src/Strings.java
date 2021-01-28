import java.sql.SQLOutput;

public class Strings {
    public static void main(String[] args) {
        int numOfDogs = 1;
        int numOfCats = 0;

        System.out.println("You have " + numOfDogs + " dogs, and " + numOfCats + " cats.");
        System.out.println("You have a total of " + (numOfDogs + numOfCats) + " pets.");
    }
}