public class Exercise10 {
    // 1. Add a `main` method.
    // 2. Create method that accepts a String and returns that string with all of its whitespace remove.
    // 2. Call your method in various ways in the main method.

    public static void main(String[] args) {
        String value = "The buoyant elephant floated to shore";
        System.out.println(removeWhitespace(value));

        System.out.println(removeWhitespace("This is a second test value"));
        System.out.println(removeWhitespace("S u p e r c a l i f r a g i l i s t i c e x p i a l i d o c i o u s"));
        System.out.println(removeWhitespace("P n e u m o n o u l t r a m i c r o s c o p i c s i l i c o v o l c a n o c o n i o s i s"));
        System.out.println(removeWhitespace("Lorem ipsum dolor sit amet"));
    }

    public static String removeWhitespace(String value) {
        String newValue = "";

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != ' ') {
                newValue += value.charAt(i);
            }
        }

        return newValue;
    }

}
