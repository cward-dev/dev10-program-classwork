package learn.secret.data;

import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class writeRepository {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("What is your secret message?: ");
        String message = console.nextLine();

        writeToFile(message);

    }

    public static void writeToFile(String message) {
        File file = new File("./secret-message.txt");

        try (PrintWriter writer = new PrintWriter("./secret-message.txt")) {
            writer.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
