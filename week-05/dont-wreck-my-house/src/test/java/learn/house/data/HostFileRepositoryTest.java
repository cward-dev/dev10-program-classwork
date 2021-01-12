package learn.house.data;

import learn.house.models.Host;
import learn.house.models.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    private final static String TEST_PATH = "./data/testing/hosts-test.csv";
    private final static String SEED_PATH = "./data/testing/hosts-seed.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path testPath = Paths.get(TEST_PATH);
        Path seedPath = Paths.get(SEED_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> actual = repository.findAll();

        assertEquals(1000, actual.size());
    }

    @Test
    void shouldFindById() {
        Host actual = repository.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        Host expected = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
                "Yearnes",
                "eyearnes0@sfgate.com",
                "(806) 1783815",
                "3 Nova Trail", "Amarillo", State.TEXAS, 79182,
                new BigDecimal("340"),
                new BigDecimal("425"));

        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getState(), actual.getState());
        assertEquals(expected.getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getStandardRate(), actual.getStandardRate());
        assertEquals(expected.getWeekendRate(), actual.getWeekendRate());
    }

    @Test
    void shouldReturnNullIfIdNotPresent() {
        Host actual = repository.findById("thisisno-tava-lidi-dand-shouldnotrun");

        assertNull(actual);
    }

    @Test
    void shouldFindByEmail() {
        Host actual = repository.findByEmail("eyearnes0@sfgate.com");
        Host expected = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
                "Yearnes",
                "eyearnes0@sfgate.com",
                "(806) 1783815",
                "3 Nova Trail", "Amarillo", State.TEXAS, 79182,
                new BigDecimal("340"),
                new BigDecimal("425"));

        assertEquals(expected, actual);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", actual.getId());
        assertEquals("Yearnes", actual.getLastName());
        assertEquals(expected.getState(), actual.getState());
    }

    @Test
    void shouldReturnNullIfEmailNotPresent() {
        Host actual = repository.findByEmail("doesntexist@fakemail.com");

        assertNull(actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Host actual = new Host("will generate",
                "Ward",
                "cward@dev-10.com",
                "(806) 1783815",
                "123 Wisconsin Street", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("150"),
                new BigDecimal("250"));

        actual = repository.add(actual);

        assertNotNull(actual);
        assertNotEquals("will generate", actual.getId());
        assertNotNull(repository.findById(actual.getId()));
    }

    @Test
    void shouldNotAddNullHost() throws DataException {
        Host actual = null;
        actual = repository.add(actual);

        assertNull(actual);
    }

    @Test
    void shouldUpdate() throws DataException {
        Host actual = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
                "UpdatedName",
                "eyearnes0@sfgate.com",
                "(806) 1783815",
                "3 Nova Trail", "Amarillo", State.TEXAS, 79182,
                new BigDecimal("340"),
                new BigDecimal("425"));

        boolean success = repository.update(actual);

        assertTrue(success);
        assertEquals("UpdatedName", repository.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15").getLastName());
    }

    @Test
    void shouldNotUpdateNullHost() throws DataException {
        Host actual = null;
        boolean success = repository.update(actual);

        assertFalse(success);
    }

    @Test
    void shouldDeleteById() throws DataException {
        String hostId = "3edda6bc-ab95-49a8-8962-d50b53f84b15";
        boolean success = repository.deleteById("3edda6bc-ab95-49a8-8962-d50b53f84b15");

        assertTrue(success);
        assertNull(repository.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15"));
    }

    @Test
    void shouldNotDeleteIfIdNotPresent() throws DataException {
        String hostId = "thisisno-tava-lidi-dand-shouldnotrun";
        boolean success = repository.deleteById(hostId);

        assertFalse(success);
    }

}