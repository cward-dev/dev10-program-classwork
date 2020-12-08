public class Exercise20 {

    public static void main(String[] args) {

        // From Alice Roosevelt Longworth:
        String aliceQuote = "If you can't say something good about someone, sit right here by me.";

        char tenthChar = aliceQuote.charAt(9); // To get the tenth char this should have been at index 9, not 10 :)
        System.out.println(tenthChar);

        // 1. Store the first character from aliceQuote in a char variable.
        // 2. Print it.
        // 3. Print the 20th character.
        // 4. Print the 68th character.

        char firstChar = aliceQuote.charAt(0);
        char twentiethChar = aliceQuote.charAt(19);
        char sixtyEighthChar = aliceQuote.charAt(67);

        System.out.println(firstChar);
        System.out.println(twentiethChar);
        System.out.println(sixtyEighthChar);
    }
}
