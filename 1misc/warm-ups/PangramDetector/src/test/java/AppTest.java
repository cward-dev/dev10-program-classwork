import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void checkForPangramReturnsTrue(){
        boolean expected = true;
        boolean actual = App.checkForPangram("abcdefghijklmnopqrstuvwxyz");
        assertEquals(expected, actual);
    }

    @Test
    void checkForPangramReturnsTrueWithCapitals(){
        boolean expected = true;
        boolean actual = App.checkForPangram("abcDefghiJklmnopqrsTuvwxyz");
        assertEquals(expected, actual);
    }

    @Test
    void checkForPangramReturnsFalse(){
        boolean expected = false;
        boolean actual = App.checkForPangram("abcdefghijklmnopqrstttwxyz");
        assertEquals(expected, actual);
    }

    @Test
    void checkForPangramReturnsTrueWithDuplicates(){
        boolean expected = true;
        boolean actual = App.checkForPangram("abcdefghijklmmmmnopqrrrstuvwxyz");
        assertEquals(expected, actual);
    }

    @Test
    void checkForPangramReturnsTrueWithChars(){
        boolean expected = true;
        boolean actual = App.checkForPangram("abcdefghijkl mnopqr stuvwxyz@%#!");
        assertEquals(expected, actual);
    }

    @Test
    void checkForPangramReturnsFalseIfShort(){
        boolean expected = false;
        boolean actual = App.checkForPangram("abcdefghij");
        assertEquals(expected, actual);
    }

}