package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepositoryDouble;
import learn.house.models.Host;
import learn.house.models.State;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    private final HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldFindAll() {
        List<Host> result = service.findAll();

        assertEquals(5, result.size());
    }

    @Test
    void shouldFindByState() {
        List<Host> result = service.findByState(State.TEXAS);

        assertEquals(2, result.size());
        assertEquals(result.get(1).getState(), State.TEXAS);
    }

    @Test
    void shouldFindEmptyByStateThatIsntPresent() {
        List<Host> result = service.findByState(State.HAWAII);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindById() {
        Host result = service.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");

        assertNotNull(result);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", result.getId());
    }

    @Test
    void shouldFindNullByIdThatDoesntExist() {
        Host result = service.findById("thisisat-esth-osta-ndsh-ouldfindnull");

        assertNull(result);
    }

    @Test
    void shouldFindByEmail() {
        Host result = service.findByEmail("eyearnes0@sfgate.com");

        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", result.getId());
    }

    @Test
    void shouldFindNullByNullEmail() {
        Host result = service.findByEmail(null);

        assertNull(result);
    }

    @Test
    void shouldFindNullByEmailThatDoesntExist() {
        Host result = service.findByEmail("thisisatestandisntpresent@testing.com");

        assertNull(result);
    }

    @Test
    void shouldAdd() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertTrue(result.isSuccess());
        assertEquals(host, result.getPayload());
    }

    @Test
    void shouldAddWithFreeRate() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("0.00"),
                new BigDecimal("0.00"));
        Result<Host> result = service.add(host);

        assertTrue(result.isSuccess());
        assertEquals(host, result.getPayload());
    }

    @Test
    void shouldNotAddNullHost() throws DataException {
        Host host = null;
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("No host to save.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostMissingLastName() throws DataException {
        Host host = new Host("will generate",
                " ",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host last name is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostMissingEmail() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                " ",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host email is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostMissingPhone() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                " ",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host phone is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostMissingAddress() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                " ", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host address is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostMissingCity() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", " ", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host city is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostWithNullState() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", null, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host state is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostWithInvalidPostalCode() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 0,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host postal code is required and must be a 5 digit number.", result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostWithNullStandardRate() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                null,
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host standard rate is required and must be a positive dollar amount.", result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostWithInvalidStandardRate() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("-1.00"),
                new BigDecimal("150"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host standard rate is required and must be a positive dollar amount.", result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostWithNullWeekendRate() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                null);
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host weekend rate is required and must be a positive dollar amount.", result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddHostWithInvalidWeekendRate() throws DataException {
        Host host = new Host("will generate",
                "Doe",
                "jdoe@gmail.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("-1.00"));
        Result<Host> result = service.add(host);

        assertFalse(result.isSuccess());
        assertEquals("Host weekend rate is required and must be a positive dollar amount.", result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() throws DataException {
        Host updatedHost = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
                "Learnes",
                "eyearnes0@sfgate.com",
                "(806) 1783815",
                "3 Nova Trail", "Amarillo", State.TEXAS, 79182,
                new BigDecimal("340"),
                new BigDecimal("425"));
        Result<Host> result = service.update(updatedHost);

        assertTrue(result.isSuccess());
        assertEquals(updatedHost, result.getPayload());
        assertEquals("Learnes", service.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15").getLastName());
    }

    @Test
    void shouldNotUpdateNullHost() throws DataException {
        Host updatedHost = null;
        Result<Host> result = service.update(updatedHost);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateHostWhoDoesntExist() throws DataException {
        Host updatedHost = new Host("will generate",
                "Doe",
                "thisisatestandisntpresent@testing.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.update(updatedHost);

        assertFalse(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(updatedHost, result.getPayload());
    }

    @Test
    void shouldNotUpdateHostToEmailThatAlreadyExists() throws DataException {
        Host updatedHost = new Host("will generate",
                "Doe",
                "krhodes1@posterous.com",
                "(111) 2223333",
                "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
                new BigDecimal("100"),
                new BigDecimal("150"));
        Result<Host> result = service.update(updatedHost);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById() throws DataException {
        String hostId = "3edda6bc-ab95-49a8-8962-d50b53f84b15";
        Result<Host> result = service.deleteById(hostId);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(hostId, result.getPayload().getId());
    }

    @Test
    void shouldNotDeleteByIdThatDoesntExist() throws DataException {
        String hostId = "thisisat-esth-osta-ndsh-ouldfindnull";
        Result<Host> result = service.deleteById(hostId);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
}