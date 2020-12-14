public class Exercise15 {

    // 1. Create a new method in the Hero class.
    // Name: toLine
    // Inputs: none
    // Output: String
    // Description: returns the Hero's name and powers as a single line of text.

    public static void main(String[] args) {

        // 2. Instantiate your three favorite super heroes with appropriate powers.
        // 3. Use the `toLine` method to print each hero's details to the console.

        Power healingFactor = new Power("Healing Factor");
        Power superStrength = new Power("Superhuman Strength");
        Power superReflexes = new Power("Superhuman Reflexes");
        Power superSpeed = new Power("Superhuman Speed");
        Power superDurability = new Power("Superhuman Durability");

        Hero[] heroes = {
                new Hero("Deadpool", new Power[]{ healingFactor, new Power("Technopathy") }),
                new Hero("The Hulk", new Power[]{ superStrength, superDurability, healingFactor }),
                new Hero("The Flash", new Power[]{ superSpeed, superReflexes, healingFactor })
        };

        for (Hero h : heroes) {
            System.out.println(h.toLine());
        }
    }
}
