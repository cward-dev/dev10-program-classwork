import java.util.Scanner;

public class Exercise11 {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("Start: ");
        int start = Integer.parseInt(console.nextLine());

        System.out.print("End: ");
        int end = Integer.parseInt(console.nextLine());

        System.out.print("Increment: ");
        int increment = Integer.parseInt(console.nextLine());

        int sum = 0;
        for (int i = start; i <= end; i += increment) {
            sum += i;
        }

        System.out.println(sum);
    }
}
