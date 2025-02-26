public class Exercise02 {

    public static void main(String[] args) {

        // 1. Add a getter for the rating field in Musician.

        Musician ocean = new Musician("Frank Ocean", 10);
        System.out.println(ocean.getName());
        // 2. Uncomment the line below and insure that it compiles and runs.

        System.out.println(ocean.getRating());

        // 3. Instantiate two musicians and assign them to new variables.
        // 4. Print each musicians' name and rating on a single line.

        Musician gaga = new Musician("Lady Gaga", 9);
        Musician black = new Musician("Rebecca Black", 2);

        System.out.printf("Name: %s, Rating %d%n", ocean.getName(), ocean.getRating());
        System.out.printf("Name: %s, Rating %d%n", gaga.getName(), gaga.getRating());
        System.out.printf("Name: %s, Rating %d%n", black.getName(), black.getRating());
    }
}
