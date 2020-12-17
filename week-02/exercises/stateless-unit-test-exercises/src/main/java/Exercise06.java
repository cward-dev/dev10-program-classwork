public class Exercise06 {

    // 1. Read the capitalizeAll JavaDocs.
    // 2. Implement capitalizeAll.
    // 3. Implement suggestions in Exercise06Test.
    // 4. Confirm implementation correctness by running tests.
    // 5. Stretch Goal: instead of capitalizing the first character of each element, capitalize the first character
    // of each word in each element.

    /**
     * Capitalizes the first character of each element in a String[]
     * and returns the result in a new array.
     * A null argument should return null.
     * An empty array should return a new empty array.
     * Null or empty array elements should be ignored.
     *
     * @param values an array containing elements to capitalize.
     * @return a new String[] with each element capitalized.
     */
    public String[] capitalizeAll(String[] values) {
        if (values == null) {
            return null;
        }

        String[] newValues = new String[values.length];
        String newValue;
        char previousChar;

        for (int i = 0; i < values.length; i++) {
            newValue = "";
            previousChar = ' '; // Both at start of each element and start of each new word

            if (values[i] != null) {
                for (int j = 0; j < values[i].length(); j++) {
                    if (previousChar == ' ') {
                        newValue += Character.toUpperCase(values[i].charAt(j));
                    } else {
                        newValue += values[i].charAt(j);
                    }
                    previousChar = values[i].charAt(j);
                }
                newValues[i] = newValue;
            }
        }

        return newValues;
    }
}
