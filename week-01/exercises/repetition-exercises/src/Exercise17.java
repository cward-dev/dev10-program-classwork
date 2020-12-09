import java.util.Scanner;

public class Exercise17 {

    public static void main(String[] args) {
        // USER-DEFINED BOX
        // 1. Collect the following from a user: rows, columns, box character, border character.
        // 2. Use nested loops to print a user-defined box in the console.
        // (See Exercise16.)

        Scanner console = new Scanner(System.in);

        System.out.print("Number of rows: ");
        int rows = console.nextInt();
        System.out.print("Number of columns: ");
        int columns = console.nextInt();

        console.nextLine();

        System.out.println("Box Character: ");

        String boxCharInput = console.nextLine();

        System.out.println("Border Character: ");
        String borderCharInput = console.nextLine();

        char boxChar = boxCharInput.charAt(0);
        char borderChar = borderCharInput.charAt(0);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (row == 0 || row == rows - 1 || col == 0 || col == columns - 1) {
                    System.out.print(borderChar);
                } else {
                    System.out.print(boxChar);
                }
            }
            System.out.println();
        }
    }
}
