import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    List<Object> testList = new ArrayList<>();

    @BeforeEach
    void setup() {
        List<String> nullList = new ArrayList<>();
        nullList.add(null);

        testList.addAll(Arrays.asList( "one", Arrays.asList("two", "three", Arrays.asList("four", "five")), null, "six", nullList, "seven", 8, Arrays.asList(9, 10, Arrays.asList(11)), "eleventy-two"));
    }


    @Test
    void shouldFlattenList() {
        List<Object> actual = App.flattenList(testList);
        assertNotNull(actual);

        List<Object> expected = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", 8, 9, 10, 11, "eleventy-two");

        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));
        assertEquals(expected.get(5), actual.get(5));
        assertEquals(expected.get(6), actual.get(6));
        assertEquals(expected.get(7), actual.get(7));
        assertEquals(expected.get(8), actual.get(8));
        assertEquals(expected.get(9), actual.get(9));
        assertEquals(expected.get(10), actual.get(10));
        assertEquals(expected.get(11), actual.get(11));
    }
}