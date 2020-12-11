import java.util.Random;

public class Exercise15 {

    public static void main(String[] args) {
        int[] one = makeRandomArray();
        int[] two = makeRandomArray();

        // 1. Create a new int[] with room enough for all elements in `one` and `two`.
        // 2. Copy elements from `one` into the beginning of the array.
        // 3. Copy elements from `two` at the end of the array.
        // 4. Print the results to confirm that it worked.

        int count = 0;

        for (int i = 0; i < one.length; i++) {
            count++;
        }

        for (int i = 0; i < two.length; i++) {
            count++;
        }

        int[] bothArrays = new int[count];
        int nextIndex = 0;

        for (int i = 0; i < one.length; i++) {
            bothArrays[nextIndex] = one[i];
            nextIndex++;
        }

        for (int i = 0; i < two.length; i++) {
            bothArrays[nextIndex] = two[i];
            nextIndex++;
        }

        for (int i = 0; i < bothArrays.length; i++) {
            System.out.println(bothArrays[i]);
        }




    }

    public static int[] makeRandomArray() {
        Random random = new Random();
        int[] result = new int[random.nextInt(100) + 50];
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(1000) - 500;
        }
        return result;
    }
}
