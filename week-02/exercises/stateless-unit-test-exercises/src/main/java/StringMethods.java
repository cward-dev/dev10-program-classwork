public class StringMethods {

    public static boolean startsWithDayOfWeek(String value) {
        if (value == null) {
            return false;
        }

        if (value.startsWith("Mon")) {
            return true;
        } else if (value.startsWith("Tues")) {
            return true;
        } else if (value.startsWith("Weds")) {
            return true;
        } else if (value.startsWith("Thurs")) {
            return true;
        } else if (value.startsWith("Fri")) {
            return true;
        } else if (value.startsWith("Sat")) {
            return true;
        } else return value.startsWith("Sun");
    }

    public static boolean containsDayOfWeek(String value) {
        if (value == null) {
            return false;
        }

        if (value.contains("Mon")) {
            return true;
        } else if (value.contains("Tues")) {
            return true;
        } else if (value.contains("Weds")) {
            return true;
        } else if (value.contains("Thurs")) {
            return true;
        } else if (value.contains("Fri")) {
            return true;
        } else if (value.contains("Sat")) {
            return true;
        } else return value.contains("Sun");
    }

    public static String removeVowelFromBetweenX(String value) {
        if (value == null) {
            return "";
        }

        String newString = "";

        for (int i = 0; i < value.length(); i++) {
            if (i > 0 && i < value.length() - 1) {
                if (value.charAt(i) == 'a' || value.charAt(i) == 'e' || value.charAt(i) == 'i' || value.charAt(i) == 'o' || value.charAt(i) == 'u') {
                    if (value.charAt(i - 1) != 'x' && value.charAt(i + 1) != 'x') {
                        newString += value.charAt(i);
                    }
                } else {
                    newString += value.charAt(i);
                }
            } else {
                newString += value.charAt(i);
            }
        }

        return newString;
    }

}
