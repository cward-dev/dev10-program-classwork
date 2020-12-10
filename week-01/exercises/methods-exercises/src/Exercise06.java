public class Exercise06 {

    // 1. Create a method.
    // Name: isBetween
    // Inputs: int, int, int
    // Output: boolean
    // Description: return true if the first parameter is between the second and third parameter.
    // Otherwise, returns false.

    public static void main(String[] args) {
        // 2. Call your method in various ways to test it here.

        int firstNum = 12;
        int secondNum = 5;
        int thirdNum = 19;

        boolean result = isBetween(firstNum, secondNum, thirdNum);
        System.out.println(result);

        System.out.println(isBetween(1,2, 3));
        System.out.println(isBetween(4,1,9));
    }

    public static boolean isBetween(int first, int second, int third) {
        boolean result;

        if (first >= second && first <= third) {
            result = true;
        } else if (first >= third && first <= second) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}
