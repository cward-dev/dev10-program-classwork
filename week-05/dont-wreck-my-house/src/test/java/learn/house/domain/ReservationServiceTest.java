package learn.house.domain;

import learn.house.data.*;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.models.State;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private final HostRepositoryDouble hostRepository = new HostRepositoryDouble();
    private final GuestRepositoryDouble guestRepository = new GuestRepositoryDouble();
    private final ReservationRepositoryDouble reservationRepository = new ReservationRepositoryDouble();

    private final ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            hostRepository,
            guestRepository);

    private final Host HOST = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
            "Yearnes",
            "eyearnes0@sfgate.com",
            "(806) 1783815",
            "3 Nova Trail", "Amarillo", State.TEXAS, 79182,
            new BigDecimal("340"),
            new BigDecimal("425"));

    private final Host NONEXISTENT_HOST = new Host("thisisatestidandshouldnotreturnahost",
            "Doe",
            "jdoe@gmail.com",
            "(111) 2223333",
            "123 North Parkway", "Milwaukee", State.WISCONSIN, 53208,
            new BigDecimal("100"),
            new BigDecimal("150"));

    private final Guest GUEST = new Guest(1, "Sullivan", "Lomas",
            "slomas0@mediafire.com", "(702) 7768761",State.NEVADA);

    private final Guest NONEXISTENT_GUEST = new Guest(99,"John","Doe",
            "jdoe@gmail.com","(111) 2223333", State.WISCONSIN);

    private final LocalDate START_DATE = LocalDate.now().plusDays(10);
    private final LocalDate END_DATE = START_DATE.plusDays(5);

    ReservationServiceTest() throws IOException {
    }

    @Test
    void shouldFindByHost() {
        List<Reservation> result = service.findByHost(HOST);

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindNullByNullHost() {
        List<Reservation> result = service.findByHost(null);

        assertNull(result);
    }

    @Test
    void shouldFindEmptyByHostWithNoReservations() {
        List<Reservation> result = service.findByHost(hostRepository.findById("a0d911e7-4fde-4e4a-bdb7-f047f15615e8"));

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindEmptyByHostThatDoesntExist() {
        List<Reservation> result = service.findByHost(NONEXISTENT_HOST);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                START_DATE,
                END_DATE,
                HOST,
                GUEST,
                new BigDecimal("360")));

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(2, result.getPayload().getId());
    }

    @Test
    void shouldAddStartingOnExistingEndDate() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        END_DATE,
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(2, result.getPayload().getId());
    }

    @Test
    void shouldAddEndingOnExistingStart() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE.minusDays(9),
                        END_DATE.minusDays(10),
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(2, result.getPayload().getId());
    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Result<Reservation> result = service.add(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("No reservation to save.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullHost() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        END_DATE,
                        null,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation host is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullGuest() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        END_DATE,
                        HOST,
                        null,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation guest is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullStartDate() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        null,
                        END_DATE,
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation start date is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullEndDate() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        null,
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation end date is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddPastStartDate() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE.minusMonths(1),
                        END_DATE,
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation start date cannot be in the past.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddEndDateBeforeStartDate() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        START_DATE.minusDays(1),
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation end date must be after the start date.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddEndDateSameAsStartDate() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        START_DATE,
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation end date must be after the start date.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddHostDoesntExist() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        END_DATE,
                        NONEXISTENT_HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Host does not exist.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddGuestDoesntExist() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE,
                        END_DATE,
                        HOST,
                        NONEXISTENT_GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Guest does not exist.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddReservationStartsBeforeEndOfExistingReservation() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE.minusDays(2),
                        END_DATE,
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation conflicts with an existing reservation.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddReservationEndsAfterStartOfExistingReservation() throws DataException {
        Result<Reservation> result = service.add(
                new Reservation(0,
                        START_DATE.minusDays(6),
                        END_DATE.minusDays(6),
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation conflicts with an existing reservation.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setEndDate(reservation.getEndDate().plusDays(2));

        Result<Reservation> result = service.update(reservation);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(1, result.getPayload().getId());
        assertEquals(reservation.getEndDate(), service.findByHost(HOST).get(0).getEndDate());
    }

    @Test
    void shouldNotUpdateNullReservation() throws DataException {
        Result<Reservation> result = service.update(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("No reservation to save.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullHost() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setHost(null);

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation host is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullGuest() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setGuest(null);

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation guest is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullStartDate() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setStartDate(null);

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation start date is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullEndDate() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setEndDate(null);

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation end date is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdatePastStartDate() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setStartDate(reservation.getStartDate().minusMonths(1));

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation start date cannot be in the past.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateEndDateBeforeStartDate() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setEndDate(reservation.getStartDate().minusDays(1));

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation end date must be after the start date.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateEndDateSameAsStartDate() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setEndDate(reservation.getStartDate());

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation end date must be after the start date.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateHostDoesntExist() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setHost(NONEXISTENT_HOST);

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Host does not exist.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateGuestDoesntExist() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setGuest(NONEXISTENT_GUEST);

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Guest does not exist.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateReservationStartsBeforeEndOfExistingReservation() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));

        Result<Reservation> conflictingReservation = service.add(
                new Reservation(0,
                        reservation.getStartDate().minusDays(4),
                        reservation.getStartDate(),
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        reservation.setStartDate(reservation.getStartDate().minusDays(2));

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation conflicts with an existing reservation.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdateReservationEndsAfterStartOfExistingReservation() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));

        Result<Reservation> conflictingReservation = service.add(
                new Reservation(0,
                        reservation.getEndDate(),
                        reservation.getEndDate().plusDays(4),
                        HOST,
                        GUEST,
                        new BigDecimal("360")));

        reservation.setEndDate(reservation.getEndDate().plusDays(1));

        Result<Reservation> result = service.update(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Reservation conflicts with an existing reservation.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));

        Result<Reservation> result = service.delete(reservation);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(1, result.getPayload().getId());
        assertEquals(0, service.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteNullReservation() throws DataException {
        Result<Reservation> result = service.delete(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, service.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteIdZero() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setId(0);

        Result<Reservation> result = service.delete(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, service.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteIdNegativeNumber() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setId(99);

        Result<Reservation> result = service.delete(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, service.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteIdDoesntExist() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setId(99);

        Result<Reservation> result = service.delete(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, service.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteNullHost() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setHost(null);

        Result<Reservation> result = service.delete(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, service.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteHostThatDoesntExist() throws DataException {
        Reservation reservation = new Reservation(1,
                START_DATE.minusDays(5),
                END_DATE.minusDays(5),
                HOST,
                GUEST,
                new BigDecimal("360"));
        reservation.setHost(NONEXISTENT_HOST);

        Result<Reservation> result = service.delete(reservation);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, service.findByHost(HOST).size());
    }
}