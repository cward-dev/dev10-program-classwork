import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Instructions {


    public static void main(String[] args) {
        File file = new File("README.txt");
        System.out.println(file.exists());
        Commands nextCommand;
        String fileText = "";

        try (FileReader fileReader = new FileReader("README.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.length() >= 1) {
                    if (line.charAt(0) != '#') {
                        String[] originalLineContents = line.split(" ");
                        fileText = "";
                        if (originalLineContents.length > 2) {
                            fileText = originalLineContents[2];
                            for (int i = 3; i < originalLineContents.length; i++) {
                                fileText += " " + originalLineContents[i];
                            }
                        }

                        String[] lineContents = { originalLineContents[0], originalLineContents[1], fileText };
                        fileText = "";

                        nextCommand = Commands.valueOf(lineContents[0]);

                        switch (nextCommand) {
                            case CREATE:
                                createFile(lineContents);
                                break;
                            case APPEND:
                                appendLine(lineContents);
                                break;
                            case DELETE:
                                deleteFile(lineContents);
                                break;
                            case COPY:
                                copyFile(lineContents);
                                break;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void createFile(String[] lineContents) {
        File newFile = new File (lineContents[1]);
        try {
            if (newFile.createNewFile()) {
                System.out.println(newFile.getName() + " was created");
            } else {
                System.out.println(newFile.getName() + " already exists");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void appendLine(String [] lineContents) {
        try (FileWriter fileWriter = new FileWriter(lineContents[1], true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(lineContents[2]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void deleteFile(String[] lineContents) {
        File deletedFile = new File (lineContents[1]);
        boolean success = deletedFile.delete();
        if (success) {
            System.out.println(deletedFile.getName() + " was deleted.");
        } else {
            System.out.println(deletedFile.getName() + " was NOT deleted.");
        }
    }

    static void copyFile(String[] lineContents) {
        Path sourcePath = Paths.get(lineContents[1]);
        String[] destinationPath = lineContents[2].split("/");
        Path targetPath = Paths.get(destinationPath[1]);

        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}