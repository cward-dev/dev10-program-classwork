package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    private final static String TEST_PATH = "./data/testing/guests-test.csv";
    private final static String SEED_PATH = "./data/testing/guests-seed.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path testPath = Paths.get(TEST_PATH);
        Path seedPath = Paths.get(SEED_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
        try {
            Files.delete(Paths.get(getDeletedFilePath()));
        } catch (IOException exception) {
            // if file not found then it is already gone
        }
    }

    @Test
    void shouldFindAll() {
        List<Guest> result = repository.findAll();

        assertEquals(1000, result.size());
    }

    @Test
    void shouldFindById() {
        Guest actual = repository.findById(1);
        Guest expected = new Guest(1, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", State.NEVADA);

        assertEquals(expected, actual);
        assertEquals(1, actual.getId());
        assertEquals("Sullivan", actual.getFirstName());
        assertEquals(expected.getState(), actual.getState());
    }

    @Test
    void shouldReturnNullIfIdNotPresent() {
        Guest actual = repository.findById(100000);

        assertNull(actual);
    }

    @Test
    void shouldFindByEmail() {
        Guest actual = repository.findByEmail("slomas0@mediafire.com");
        Guest expected = new Guest(1, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", State.NEVADA);

        assertEquals(expected, actual);
        assertEquals(1, actual.getId());
        assertEquals("Sullivan", actual.getFirstName());
        assertEquals(expected.getState(), actual.getState());
    }

    @Test
    void shouldReturnNullIfEmailNotPresent() {
        Guest actual = repository.findByEmail("doesntexist@fakemail.com");

        assertNull(actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Guest actual = new Guest(0, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", State.NEVADA);
        actual = repository.add(actual);

        assertNotNull(actual);
        assertEquals(1001, actual.getId());
        assertNotNull(repository.findById(1001));
    }

    @Test
    void shouldNotAddNullGuest() throws DataException {
        Guest actual = null;
        actual = repository.add(actual);

        assertNull(actual);
        assertNull(repository.findById(1001));
    }

    @Test
    void shouldUpdate() throws DataException {
        Guest actual = new Guest(1, "Sullivan", "Thomas", "slomas0@mediafire.com", "(702) 7768761", State.NEVADA);
        boolean success = repository.update(actual);

        assertTrue(success);
        assertEquals("Thomas", repository.findById(1).getLastName());
    }

    @Test
    void shouldNotUpdateNullGuest() throws DataException {
        Guest original = new Guest(1, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", State.NEVADA);
        Guest actual = null;
        boolean success = repository.update(actual);

        assertFalse(success);
        assertEquals(original, repository.findById(1));
    }

    @Test
    void shouldDeleteById() throws DataException {
        int guestId = 1;
        boolean success = repository.delete(repository.findById(guestId));

        assertTrue(success);
        assertNull(repository.findById(guestId));
        assertEquals(1, repository.findAllDeleted().size());
    }

    @Test
    void shouldNotDeleteIfIdNotPresent() throws DataException {
        int guestId = 100000;
        boolean success = repository.delete(repository.findById(guestId));

        assertFalse(success);
        assertEquals(0, repository.findAllDeleted().size());
    }

    private String getDeletedFilePath() {
        return TEST_PATH.substring(0, TEST_PATH.length() - 4) + "-inactivated.csv";
    }
}