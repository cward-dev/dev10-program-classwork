public class Exercise01 {

    public static void main(String[] args) {

        Musician ocean = new Musician("Frank Ocean", 10);
        System.out.println(ocean.getName());

        // 1. Find the Musician class in this project.
        // 2. Read its constructor comments.
        // 3. Instantiate two more musicians and assign them to new variables.
        // 4. Print the musicians' names to the console.

        Musician gaga = new Musician("Lady Gaga", 9);
        Musician black = new Musician("Rebecca Black", 2);

        System.out.println(gaga.getName());
        System.out.println(black.getName());

    }
}
