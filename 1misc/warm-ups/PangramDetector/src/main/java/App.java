public class App {

    public static boolean checkForPangram(String phrase) {
        boolean[] lettersPresent = new boolean[26]; // 0='A', 25='Z'
        char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

        for (int i = 0; i < phrase.length(); i++) {
            for (int j = 0; j < letters.length; j ++) {
                if (Character.toLowerCase(phrase.charAt(i)) == letters[j]) {
                    lettersPresent[j] = true;
                }
            }
        }
        for (int i = 0; i < lettersPresent.length; i++) {
            if (lettersPresent[i] == false) {
                return false;
            }
        }
        return true;
    }

}
