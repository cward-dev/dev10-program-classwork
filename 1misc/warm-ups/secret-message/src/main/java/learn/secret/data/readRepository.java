package learn.secret.data;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class readRepository {

    public static void main(String[] args) {
        readFile("./secret-message.txt");
    }
    public static void readFile(String filepath){

        try(FileReader fileReader = new FileReader(filepath);BufferedReader reader = new BufferedReader(fileReader)) {
            for(String line= reader.readLine();line != null; line = reader.readLine()){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("This file does not exist.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
