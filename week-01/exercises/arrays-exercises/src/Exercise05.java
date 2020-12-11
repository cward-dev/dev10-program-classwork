public class Exercise05 {

    public static void main(String[] args) {
        // 1. Declare an array to hold the names of the world's continents.
        // Do not use array literal notation. Allocate space for 6 continents and then set each value by index.
        // 2. Loop over each element and print it.

        String[] continents = new String[7];

        continents[0] = "north america";
        continents[1] = "south america";
        continents[2] = "europe";
        continents[3] = "africa";
        continents[4] = "asia";
        continents[5] = "australia";
        continents[6] = "antarctica";

        for (int i = 0; i < continents.length; i++) {
            System.out.println(continents[i]);
        }
    }
}
