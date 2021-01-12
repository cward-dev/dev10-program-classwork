package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.models.Guest;
import learn.house.models.State;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    private final GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindAll() {
        List<Guest> result = service.findAll();

        assertEquals(5, result.size());
    }

    @Test
    void shouldFindByState() {
        List<Guest> result = service.findByState(State.WASHINGTON_DC);

        assertEquals(2, result.size());
        assertEquals(result.get(1).getState(), State.WASHINGTON_DC);
    }

    @Test
    void shouldFindEmptyByStateThatIsntPresent() {
        List<Guest> result = service.findByState(State.HAWAII);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindById() {
        Guest result = service.findById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void shouldFindNullByIdThatDoesntExist() {
        Guest result = service.findById(100000);

        assertNull(result);
    }

    @Test
    void shouldFindByEmail() {
        Guest result = service.findByEmail("slomas0@mediafire.com");

        assertEquals(1, result.getId());
    }

    @Test
    void shouldFindNullByNullEmail() {
        Guest result = service.findByEmail(null);

        assertNull(result);
    }

    @Test
    void shouldFindNullByNotPresentEmail() {
        Guest result = service.findByEmail("thisisatestandisntpresent@testing.com");

        assertNull(result);
    }

    @Test
    void shouldAdd() throws DataException {
        Guest guest = new Guest(0,"John","Doe",
                "jdoe@gmail.com","(111) 2223333", State.WISCONSIN);
        Result<Guest> result = service.add(guest);

        assertTrue(result.isSuccess());
        assertEquals(guest, result.getPayload());
    }

    @Test
    void shouldNotAddNullGuest() throws DataException {
        Guest guest = null;
        Result<Guest> result = service.add(guest);

        assertFalse(result.isSuccess());
        assertEquals("No guest to save.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddGuestMissingFirstName() throws DataException {
        Guest guest = new Guest(0," ","Doe",
                "jdoe@gmail.com","(111) 2223333", State.WISCONSIN);        Result<Guest> result = service.add(guest);

        assertFalse(result.isSuccess());
        assertEquals("Guest first name is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddGuestMissingLastName() throws DataException {
        Guest guest = new Guest(0,"John"," ",
                "jdoe@gmail.com","(111) 2223333", State.WISCONSIN);        Result<Guest> result = service.add(guest);

        assertFalse(result.isSuccess());
        assertEquals("Guest last name is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddGuestMissingEmail() throws DataException {
        Guest guest = new Guest(0,"John","Doe",
                " ","(111) 2223333", State.WISCONSIN);        Result<Guest> result = service.add(guest);

        assertFalse(result.isSuccess());
        assertEquals("Guest email is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddGuestMissingPhone() throws DataException {
        Guest guest = new Guest(0,"John","Doe",
                "jdoe@gmail.com"," ", State.WISCONSIN);        Result<Guest> result = service.add(guest);

        assertFalse(result.isSuccess());
        assertEquals("Guest phone is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddGuestWithNullState() throws DataException {
        Guest guest = new Guest(0,"John","Doe",
                "jdoe@gmail.com","(111) 2223333", null);        Result<Guest> result = service.add(guest);

        assertFalse(result.isSuccess());
        assertEquals("Guest state is required.",result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() throws DataException {
        Guest updatedGuest = new Guest(1, "Sullivan", "Thomas",
                "slomas0@mediafire.com", "(702) 7768761", State.NEVADA);
        Result<Guest> result = service.update(updatedGuest);

        assertTrue(result.isSuccess());
        assertEquals(updatedGuest, result.getPayload());
    }

    @Test
    void shouldNotUpdateNullGuest() throws DataException {
        Guest updatedGuest = null;
        Result<Guest> result = service.update(updatedGuest);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateGuestWhoDoesntExist() throws DataException {
        Guest updatedGuest = new Guest(100000, "John", "Doe",
                "thisisatestandisntpresent@testing.com", "(111) 2223333", State.WISCONSIN);
        Result<Guest> result = service.update(updatedGuest);

        assertFalse(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateGuestToEmailThatAlreadyExists() throws DataException {
        Guest updatedGuest = new Guest(1, "Sullivan", "Lomas",
                "ogecks1@dagondesign.com", "(702) 7768761", State.NEVADA);
        Result<Guest> result = service.update(updatedGuest);

        assertFalse(result.isSuccess());
        assertEquals("Guest email 'ogecks1@dagondesign.com' is already in use.", result.getErrorMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById() throws DataException {
        int guestId = 1;
        Result<Guest> result = service.deleteById(guestId);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(guestId, result.getPayload().getId());
    }

    @Test
    void shouldNotDeleteByIdThatDoesntExist() throws DataException {
        int guestId = 100000;
        Result<Guest> result = service.deleteById(guestId);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

}