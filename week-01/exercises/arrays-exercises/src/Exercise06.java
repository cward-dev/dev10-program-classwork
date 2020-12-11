public class Exercise06 {

    public static void main(String[] args) {
        int[] values = {18, 42, 54, 93, 22};

        // 1. Create a loop to calculate the sum of elements in `values`.
        // 2. Print the result.

        int sum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }

        System.out.println(sum);
    }
}
