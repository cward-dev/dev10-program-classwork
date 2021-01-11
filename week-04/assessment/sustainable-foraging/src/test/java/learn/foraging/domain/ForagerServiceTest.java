package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForagerRepositoryDouble;
import learn.foraging.models.Forager;
import learn.foraging.models.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForagerServiceTest {

    ForagerService service = new ForagerService(new ForagerRepositoryDouble());

    @Test
    void shouldAdd() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);

        assertTrue(result.isSuccess());
        assertEquals(forager, result.getPayload());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Forager forager = null;

        Result<Forager> result = service.add(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("No forager to save.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddMissingFirstName() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName(" ");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Forager first name is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddMissingLastName() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName(" ");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Forager last name is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddMissingState() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(null);

        Result<Forager> result = service.add(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Forager state is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicate() throws DataException {
        Forager forager1 = new Forager();
        forager1.setId("AAAA-1111-2222-FFFF");
        forager1.setFirstName("John");
        forager1.setLastName("Doe");
        forager1.setState(State.WISCONSIN);

        Result<Forager> result1 = service.add(forager1);

        assertTrue(result1.isSuccess());

        Result<Forager> result2 = service.add(forager1);

        assertFalse(result2.isSuccess());
        assertNull(result2.getPayload());
        assertEquals("Forager John Doe from WI already exists.", result2.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicateWhiteSpace() throws DataException {
        Forager forager1 = new Forager();
        forager1.setId("AAAA-1111-2222-FFFF");
        forager1.setFirstName("John");
        forager1.setLastName("Doe");
        forager1.setState(State.WISCONSIN);

        Result<Forager> result1 = service.add(forager1);
        assertTrue(result1.isSuccess());

        forager1.setFirstName("John      ");
        Result<Forager> result2 = service.add(forager1);

        assertFalse(result2.isSuccess());
        assertNull(result2.getPayload());
        assertEquals("Forager John Doe from WI already exists.", result2.getErrorMessages().get(0));
    }

    @Test
    void shouldUpdate() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);
        assertTrue(result.isSuccess());

        forager.setLastName("Deer");
        result = service.update(forager);

        assertTrue(result.isSuccess());
        assertEquals(forager, result.getPayload());
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);
        assertTrue(result.isSuccess());

        forager = null;
        result = service.update(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateMissingFirstName() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);
        assertTrue(result.isSuccess());

        forager.setFirstName(" ");
        result = service.update(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateMissingLastName() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);
        assertTrue(result.isSuccess());

        forager.setLastName(" ");
        result = service.update(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullState() throws DataException {
        Forager forager = new Forager();

        forager.setId("AAAA-1111-2222-FFFF");
        forager.setFirstName("John");
        forager.setLastName("Doe");
        forager.setState(State.WISCONSIN);

        Result<Forager> result = service.add(forager);
        assertTrue(result.isSuccess());

        forager.setState(null);
        result = service.update(forager);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
}