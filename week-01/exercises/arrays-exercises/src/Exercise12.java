import java.util.Random;

public class Exercise12 {

    public static void main(String[] args) {
        int[] values = makeRandomArray();

        // 1. Count the number of positive and non-positive elements in `values`.
        // 2. Create two new int[]s, one for positive elements and one for non-positive.
        // (We count first to determine the capacity to allocate.)
        // 3. Loop through `values` a second time. If an element is positive, add it to the positive array.
        // If it is non-positive, add it to the non-positive array.
        // 4. Confirm that your secondary arrays are properly populated either by debugging or printing their elements.

        int countPos = 0;
        int countNeg = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > 0) {
                countPos++;
            } else if (values[i] < 0) {
                countNeg++;
            }
        }

        int[] posValues = new int[countPos];
        int nextPosIndex = 0;
        int[] negValues = new int[countNeg];
        int nextNegIndex = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > 0) {
                posValues[nextPosIndex] = values[i];
                nextPosIndex++;
            } else if (values[i] < 0) {
                negValues[nextNegIndex] = values[i];
                nextNegIndex++;
            }
        }

        System.out.println("Positive Values:");
        for (int i = 0; i < posValues.length; i++) {
            System.out.println(posValues[i]);
        }


        System.out.println("Negative Values:");
        for (int i = 0; i < negValues.length; i++) {
            System.out.println(negValues[i]);
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
