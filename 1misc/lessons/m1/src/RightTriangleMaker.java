import java.util.Scanner;

public class RightTriangleMaker {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        int numRows;
        String symbol = "";
        String symbolChar = "";

        System.out.println("Number of rows: ");
        numRows = Integer.parseInt(console.nextLine());


        System.out.println("Character: ");
        symbol = console.nextLine();
        symbolChar += symbol.charAt(0);

        System.out.println();

        for (int i = 1; i <= numRows; i++) {
            System.out.print(" ".repeat(numRows-i));
            System.out.println(symbolChar.repeat(i));
        }
    }
}
