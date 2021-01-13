package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    private final static String SEED_FILE_PATH = "./data/testing/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c-seed.csv";
    private final static String TEST_FILE_PATH = "./data/testing/reservations-test/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv";
    private final static String TEST_DIR_PATH = "./data/testing/reservations-test/";

    HostRepository hostRepository = new HostFileRepository("./data/testing/hosts-test.csv");
    Host HOST = hostRepository.findById("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c"); // line 322 in hosts-test.csv
    Host HOST_WITHOUT_RESERVATIONS = hostRepository.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");

    ReservationRepository repository = new ReservationFileRepository(
            TEST_DIR_PATH,
            new GuestFileRepository("./data/testing/guests-test.csv"),
            new HostFileRepository("./data/testing/hosts-test.csv"));

    @BeforeEach
    void setup() throws IOException {
        Path testPath = Paths.get(TEST_FILE_PATH);
        Path seedPath = Paths.get(SEED_FILE_PATH);

        try {
            Files.delete(Paths.get("./data/testing/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv"));
        } catch (IOException exception) {
            // if not found then file is already not present
        }

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

//    @Test
//    void testingReservationModelCalculateTotal() { // TODO MATH SEEMS BUGGY BUT WORKS?
//        String hostId = "2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c";
//        ReservationRepository testRepository = new ReservationFileRepository(
//                "./data/testing/" + hostId + ".csv",
//                new GuestFileRepository("./data/testing/guests-test.csv"),
//                new HostFileRepository("./data/testing/hosts-test.csv"));
//
//        List<Reservation> reservations = repository.findByHost(HOST);
//
//        for (Reservation reservation : reservations) {
//            assertEquals(reservation.getTotal(), reservation.calculateTotal());
//        }
//    }

    @Test
    void shouldFindByHost() {
        List<Reservation> reservations = repository.findByHost(HOST);

        assertNotNull(reservations);
        assertEquals(13, reservations.size());
    }

    @Test
    void shouldReturnEmptyIfNullHost() {
        List<Reservation> reservations = repository.findByHost(null);

        assertNotNull(reservations);
        assertEquals(0, reservations.size());
    }

    @Test
    void shouldReturnEmptyIfHostHasNoReservations() {
        List<Reservation> reservations = repository.findByHost(HOST_WITHOUT_RESERVATIONS);

        assertNotNull(reservations);
        assertEquals(0, reservations.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 1, 1));
        reservation.setEndDate(LocalDate.of(2025, 1, 3));

        Host host = new Host();
        host.setId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");
        reservation.setHost(host);

        Guest guest = new Guest();
        guest.setId(99);
        reservation.setGuest(guest);

        reservation.setTotal(new BigDecimal(360));

        reservation = repository.add(reservation);

        assertNotNull(reservation);
        assertEquals(14, reservation.getId());
        assertEquals(14, repository.findByHost(host).size());
    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Reservation reservation = null;

        reservation = repository.add(reservation);

        assertNull(reservation);
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 1, 1));
        reservation.setEndDate(LocalDate.of(2025, 1, 3));

        reservation.setHost(HOST);

        Guest guest = new Guest();
        guest.setId(99);
        reservation.setGuest(guest);

        reservation.setTotal(new BigDecimal(360));

        reservation = repository.add(reservation);

        reservation.setEndDate(LocalDate.of(2020,1,2));
        boolean success = repository.update(reservation);

        assertTrue(success);
        assertNotNull(reservation);
        assertEquals(14, repository.findByHost(HOST).size());
    }

    @Test
    void shouldNotUpdateNullReservation() throws DataException {
        Reservation reservation = null;

        boolean success = repository.update(reservation);

        assertFalse(success);
        assertEquals(13, repository.findByHost(HOST).size());
    }

    @Test
    void shouldNotUpdateReservationThatDoesntExist() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 1, 1));
        reservation.setEndDate(LocalDate.of(2025, 1, 3));

        reservation.setHost(HOST);

        Guest guest = new Guest();
        guest.setId(99);
        reservation.setGuest(guest);
        reservation.setTotal(new BigDecimal(360));

        reservation.setEndDate(LocalDate.of(2020,1,2));
        boolean success = repository.update(reservation);

        assertFalse(success);
    }

    @Test
    void shouldNotUpdateHostWithNoReservationsFile() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 1, 1));
        reservation.setEndDate(LocalDate.of(2025, 1, 3));

        reservation.setHost(HOST_WITHOUT_RESERVATIONS);

        Guest guest = new Guest();
        guest.setId(99);
        reservation.setGuest(guest);
        reservation.setTotal(new BigDecimal(360));

        reservation.setEndDate(LocalDate.of(2020,1,2));
        boolean success = repository.update(reservation);

        assertFalse(success);
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setHost(HOST);

        boolean success = repository.delete(reservation);

        assertTrue(success);
        assertEquals(12, repository.findByHost(HOST).size());
    }

    @Test
    void shouldNotDeleteNullReservation() throws DataException {
        Reservation reservation = null;

        boolean success = repository.delete(reservation);

        assertFalse(success);
    }

    @Test
    void shouldNotDeleteReservationThatDoesntExist() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(99);
        reservation.setHost(HOST);

        boolean success = repository.delete(reservation);

        assertFalse(success);
    }

    @Test
    void shouldNotDeleteHostWithNoReservationsFile() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setHost(HOST_WITHOUT_RESERVATIONS);

        boolean success = repository.delete(reservation);

        assertFalse(success);
    }
}