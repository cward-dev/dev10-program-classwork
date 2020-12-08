public class Exercise15 {

    public static void main(String[] args) {

        int value = 147;

        // 1. Declare a boolean variable inRange.
        // 2. When value is between 10 and 20 OR value is between 110 and 120, inRange is true. Otherwise, it's false.
        // 3. Build an expression using value to set inRange.
        // 4. Print the result.
        // 5. Change value to test different cases:
        // - less than 10 - false
        // - between 10 and 20 - true
        // - between 21 and 109 - false
        // - between 110 and 120 - true
        // - greater than 120 - false

        boolean inRange = (value >= 10 && value <= 20) || (value >= 110 && value <= 120);

        System.out.println(inRange);
    }
}
