import java.util.Scanner;

public class palindromeChecker {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String originalString = "";
        boolean isPalindrome;

        do {
            System.out.print("Please enter a string: ");
            originalString = console.nextLine();
        } while (originalString == null);

        String filteredString = filterString(originalString);
        isPalindrome = isPalindrome(filteredString);

        if (isPalindrome) {
            System.out.println(originalString + " is a palindrome!");
        } else {
            System.out.println(originalString + " is not a palindrome!");
        }
    }

    public static String filterString(String originalString) {
        String filteredString = "";
        for (int i = 0; i < originalString.length(); i++) {
            if (Character.isLetter(originalString.charAt(i))) {
                filteredString += originalString.charAt(i);
            }
        }
        return filteredString;
    }

    public static boolean isPalindrome(String filteredString) {
        String reversedString = "";

        for (int i = 0; i < filteredString.length(); i++) {
            reversedString += filteredString.charAt(filteredString.length() - 1 - i);
        }

        if (reversedString.equalsIgnoreCase(filteredString)) {
            return true;
        } else {
            return false;
        }
    }
}