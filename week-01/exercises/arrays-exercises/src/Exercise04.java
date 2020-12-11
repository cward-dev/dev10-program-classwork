public class Exercise04 {

    public static void main(String[] args) {
        // 1. Declare an array to hold the names of the world's oceans.
        // Set its value using array literal notation.
        // 2. Loop over each element and print it.

        String[] oceans = { "pacific", "atlantic", "indian", "arctic", "antarctic" };

        for (int i = 0; i < oceans.length; i++) {
            System.out.println(oceans[i]);
        }
    }
}
