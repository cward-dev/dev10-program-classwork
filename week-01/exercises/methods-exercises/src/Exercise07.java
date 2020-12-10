public class Exercise07 {

    // 1. Create a method.
    // Name: areInOrder
    // Inputs: int, int, int, int
    // Output: boolean
    // Description: return true if the four parameters are in ascending order.
    // Otherwise, returns false.

    public static void main(String[] args) {
        // 2. Call your method in various ways to test it here.
        System.out.println(areInOrder(1,2,3,4));
        System.out.println(areInOrder(3,2,1,4));
        System.out.println(areInOrder(3,16,23,54));
        System.out.println(areInOrder(56,75,23,99));
    }

    public static boolean areInOrder(int first, int second, int third, int fourth) {
        if (first < second && second < third && third < fourth) {
            return true;
        } else {
            return false;
        }
    }
}
