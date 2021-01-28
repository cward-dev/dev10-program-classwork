import java.util.HashMap;

public class App {

    public static void main(String[] args) {
        String value = "ACE";
        HashMap<String, String> notes = new HashMap<>();

        notes.put("A", "C#");
        notes.put("A#", "D#");
        notes.put("B", "D#");
        notes.put("C", "E");
        notes.put("C#", "F");
        notes.put("D", "F#");
        notes.put("D#", "G");
        notes.put("E", "G#");
        notes.put("F", "A");
        notes.put("F#", "A#");
        notes.put("G", "B");
        notes.put("G#", "C");

        String str;

        for (int i = 0; i < value.length(); i++) {
            if (i < value.length() - 1) {
                if (value.length() > 1 && value.charAt(i + 1) == '#') {
                    str = value.substring(i, i+2);
                    i++;
                } else {
                    str = value.substring(i, i+1);
                }
            } else {
                str = value.substring(i, i+1);
            }
            System.out.print(notes.get(str));
        }
    }

}