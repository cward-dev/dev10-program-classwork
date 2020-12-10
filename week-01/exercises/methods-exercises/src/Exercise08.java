public class Exercise08 {

    // 1. Create a method.
    // Name: getRandomFruit
    // Inputs: none
    // Output: String
    // Description: returns a random fruit name as a string.
    // See Exercise01.
    // Choose from at least 5 fruit.

    public static void main(String[] args) {
        // 2. Call your method in various ways to test it here.
        for (int i = 0; i < 15; i++) {
            System.out.println(getRandomFruit());
        }
    }

    public static String getRandomFruit() {
        int seed = (int)(Math.random() * 5);
        String fruit = "";

        switch (seed) {
            case 0:
                fruit = "apple";
                break;
            case 1:
                fruit = "banana";
                break;
            case 2:
                fruit = "cherry";
                break;
            case 3:
                fruit = "grape";
                break;
            case 4:
                fruit = "pomegranate";
                break;
        }

        return fruit;
    }
}
