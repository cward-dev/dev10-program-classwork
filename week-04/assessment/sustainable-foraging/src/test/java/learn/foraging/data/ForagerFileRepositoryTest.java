package learn.foraging.data;

import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import learn.foraging.models.Item;
import learn.foraging.models.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForagerFileRepositoryTest {

    static final String TEST_FILE_PATH = "./data/test/foragers-test.csv";
    static final String SEED_FILE_PATH = "./data/test/foragers-seed.csv";

    ForagerFileRepository repository = new ForagerFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Forager> all = repository.findAll();
        assertEquals(1000, all.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        forager = repository.add(forager);

        assertEquals(36, forager.getId().length());
        assertEquals(1001, repository.findAll().size());
    }

    @Test
    void shouldUpdate() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        forager = repository.add(forager);

        forager.setLastName("Deer");
        boolean success = repository.update(forager);

        assertTrue(success);
        assertEquals(1001, repository.findAll().size());
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        forager = repository.add(forager);

        forager = null;
        boolean success = repository.update(forager);

        assertFalse(success);
        assertEquals(1001, repository.findAll().size());
    }

    @Test
    void shouldSerializeAndDeserializeStringWithCommaCorrectly() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John, the Tester");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        forager = repository.add(forager);

        assertNotNull(forager);
        assertEquals("John, the Tester", forager.getFirstName());
    }

    @Test
    void shouldReturnWithCommaInsteadOfDelimiterIfWrittenIntoStringField() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John@@@ the Tester");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        forager = repository.add(forager);

        assertNotNull(forager);
        assertEquals("John, the Tester", forager.getFirstName());
    }
}