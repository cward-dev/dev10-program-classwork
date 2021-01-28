import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        /*
         * Input: `[ "one", [ "two", "three" ], null, "four", [ null ], "five" ]`
         * Output: `[ "one", "two", "three", "four", "five" ]`
         */

        List<String> nullList = new ArrayList<>();
        nullList.add(null);

        List<Object> testList = new ArrayList<>(Arrays.asList(
                "one",
                Arrays.asList("two", "three", Arrays.asList("four", "five")),
                null, "six", nullList, "seven", 8, Arrays.asList(9, 10, Arrays.asList(11)), "eleventy-two"));

        List<Object> result = flattenList(testList);
        System.out.println(result);

    }

    public static List<Object> flattenList(List<Object> originalList) {
        List<Object> result = new ArrayList();

        for (Object o : originalList) {
            // check if object is a nested list
            if (o instanceof List) {
                // if a nested list, call this method on that list
                result.addAll(flattenList((List<Object>) o));
            } else if (o != null) {
                // if not a list, add String value to result
                result.add(o);
            }
        }
        return result;
    }
}
