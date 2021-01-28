import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FootballRepoTest {

    private final String TEST_PATH = "./data/football-test.dat";
    private final String SEED_PATH = "./data/football-seed.dat";

    FootballRepo repo = new FootballRepo(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path testPath = Paths.get(TEST_PATH);
        Path seedPath = Paths.get(SEED_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Team> actual = repo.findAll();

        assertNotNull(actual);
        assertEquals(17, actual.size());
    }



}