package learn.unexplained.data;

import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EncounterFileRepositoryTest {

    static final String TEST_PATH = "./data/encounters-test.csv";
    static final String SEED_PATH = "./data/encounters-seed.csv";

    final Encounter[] testEncounters = new Encounter[]{
            new Encounter(1, EncounterType.UFO, "2020-01-01", "short test #1", 1),
            new Encounter(2, EncounterType.CREATURE, "2020-02-01", "short test #2", 1),
            new Encounter(3, EncounterType.SOUND, "2020-03-01", "short test #3", 1)
    };

    EncounterRepository repository = new EncounterFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Encounter> encounters = repository.findAll();
        Encounter[] actual = encounters.toArray(new Encounter[encounters.size()]);
        assertArrayEquals(testEncounters, actual);
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Encounter encounter = new Encounter();
        encounter.setType(EncounterType.UFO);
        encounter.setWhen("Jan 15, 2005");
        encounter.setDescription("moving pinpoint of light." +
                "seemed to move with me along the highway. " +
                "then suddenly reversed direction without slowing down. it just reversed.");
        encounter.setOccurrences(1);

        Encounter actual = repository.add(encounter);

        assertNotNull(actual);
        assertEquals(4, actual.getEncounterId());
    }

    @Test
    void shouldFindAllOfTypeCreatures() throws DataAccessException {
        List<Encounter> encounters = repository.findByType(EncounterType.CREATURE);

        assertNotNull(encounters);
        assertEquals(1,encounters.size());
        assertEquals(2,encounters.get(0).getEncounterId());
    }

    @Test
    void shouldFindNoneOfNotPresentType() throws DataAccessException {
        List<Encounter> encounters = repository.findByType(EncounterType.VOICE);

        assertNotNull(encounters);
        assertEquals(0,encounters.size());
    }

    @Test
    void shouldUpdateExistingEncounter() throws DataAccessException {
        List<Encounter> encounters = repository.findAll();

        Encounter actual = new Encounter(1, EncounterType.UFO, "2020-01-01", "short test #1, updated", 1);

        assertTrue(repository.update(actual));
        assertEquals("short test #1, updated", repository.findByType(EncounterType.UFO).get(0).getDescription());
    }

    @Test
    void shouldNotUpdateNotExistingEncounter() throws DataAccessException {
        List<Encounter> encounters = repository.findAll();

        Encounter actual = new Encounter(100000, EncounterType.UFO, "2020-01-01", "updated", 1);

        assertFalse(repository.update(actual));
    }

}