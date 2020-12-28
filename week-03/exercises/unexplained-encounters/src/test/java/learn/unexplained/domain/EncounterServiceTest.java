package learn.unexplained.domain;

import learn.unexplained.data.DataAccessException;
import learn.unexplained.data.EncounterRepositoryDouble;
import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EncounterServiceTest {

    EncounterService service = new EncounterService(new EncounterRepositoryDouble());

    @Test
    void shouldNotAddNull() throws DataAccessException {
        EncounterResult expected = makeResult("encounter cannot be null");
        EncounterResult actual = service.add(null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyWhen() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, " ", "test desc", 1);
        EncounterResult expected = makeResult("when is required");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyDescription() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", "  ", 1);
        EncounterResult expected = makeResult("description is required");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullDescription() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", null, 1);
        EncounterResult expected = makeResult("description is required");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddZeroOccurrences() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", "test description", 0);
        EncounterResult expected = makeResult("occurrences must be greater than 0");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddDuplicate() throws DataAccessException {
        Encounter encounter = new Encounter(1,EncounterType.CREATURE,"1/1/2020", "it was scary", 1);
        EncounterResult expected = makeResult("duplicate encounter is not allowed");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", "test description", 1);
        EncounterResult expected = new EncounterResult();
        expected.setPayload(encounter);

        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() throws DataAccessException {
        Encounter expected = new Encounter(1,EncounterType.CREATURE,"1/1/2020", "it was scary", 1);
        Encounter actual = service.findById(1);

        assertNotNull(actual);
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void shouldNotFindByInvalidId() throws DataAccessException {
        Encounter actual = service.findById(100000);

        assertNull(actual);
    }

    @Test
    void shouldFindByType() throws DataAccessException {
        List<Encounter> actual = service.findByType(EncounterType.CREATURE);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("it was scary", actual.get(0).getDescription());
    }

    @Test
    void shouldFindZeroByNotPresentType() throws DataAccessException {
        List<Encounter> actual = service.findByType(EncounterType.VISION);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        Encounter expected = new Encounter(1,EncounterType.CREATURE,"1/1/2020", "it was scary, updated", 1);
        EncounterResult result = service.update(expected);

        assertTrue(result.isSuccess());
        assertEquals(expected.getDescription(), service.findById(1).getDescription());
    }

    @Test
    void shouldNotUpdateNullEncounter() throws DataAccessException {
        Encounter expected = null;
        EncounterResult result = service.update(expected);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullWhen() throws DataAccessException {
        Encounter actual = new Encounter(1,EncounterType.CREATURE,null, "it was scary", 1);
        EncounterResult result = service.update(actual);

        assertFalse(result.isSuccess());
        assertEquals("1/1/2020", service.findById(1).getWhen());
    }

    @Test
    void shouldNotUpdateNullDescription() throws DataAccessException {
        Encounter actual = new Encounter(1,EncounterType.CREATURE,"1/1/2020", null, 1);
        EncounterResult result = service.update(actual);

        assertFalse(result.isSuccess());
        assertEquals("it was scary", service.findById(1).getDescription());
    }

    @Test
    void shouldNotUpdateDuplicate() throws DataAccessException {
        Encounter actual = new Encounter(3,EncounterType.CREATURE,"1/1/2020", "it was scary", 1);
        EncounterResult result = service.update(actual);

        assertFalse(result.isSuccess());
        assertEquals("it was loud", service.findById(3).getDescription());
    }

    @Test
    void shouldNotUpdateNotPositiveOccurrences() throws DataAccessException {
        Encounter actual = new Encounter(1,EncounterType.CREATURE,"1/1/2020", "it was scary", 0);
        EncounterResult result = service.update(actual);
        assertFalse(result.isSuccess());
        assertEquals(1, service.findById(1).getOccurrences());

        actual = new Encounter(1,EncounterType.CREATURE,"1/1/2020", "it was scary", -1);
        result = service.update(actual);
        assertFalse(result.isSuccess());
        assertEquals(1, service.findById(1).getOccurrences());

    }

    @Test
    void shouldNotUpdateNotExisting() throws DataAccessException {
        Encounter actual = new Encounter(100000,EncounterType.CREATURE,"1/1/2020", "it was scary, updated", 1);
        EncounterResult result = service.update(actual);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        EncounterResult result = service.deleteById(1);

        assertTrue(result.isSuccess());
        assertNull(service.findById(1));
    }

    @Test
    void shouldNotDeleteByIdNotExisting() throws DataAccessException {
        EncounterResult result = service.deleteById(100000);

        assertFalse(result.isSuccess());
    }

    private EncounterResult makeResult(String message) {
        EncounterResult result = new EncounterResult();
        result.addErrorMessage(message);
        return result;
    }
}