public class Exercise25 {

    // Mad Libs: https://en.wikipedia.org/wiki/Mad_Libs
    // 1. Add a main method.
    // 2. Declare several variables of various types to be "plugged in" to a Mad Libs sentence.
    // 3. Use string concatenation to build a silly sentence.
    // 4. Print the result.
    // 5. Change variable values to change the sentence. Ask a friend for random values to increase the chances
    // of something hilarious or kinda naughty.

    public static void main(String[] args) {
        String professionNoun = "plumber";
        String adjective = "sassy";
        String animal = "penguin";
        String verb = "spit";
        int number = 25;
        boolean trueOrFalse = true;

        String madLib = "The average "
                + professionNoun
                + " doesn't know this, but the "
                + adjective
                + " "
                + animal
                + " loves to "
                + verb
                + " for "
                + number
                + " days without stopping!"
                + " It's "
                + trueOrFalse
                + "!";

        System.out.println(madLib);
    }
}
