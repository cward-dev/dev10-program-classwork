package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    private final static String TEST_FILE_PATH = "./data/testing/reservations-test/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv";
    private final static String SEED_FILE_PATH = "./data/testing/reservations-seed/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv";
    private final static String TEST_DIR_PATH = "./data/testing/reservations-test/";

    HostRepository hostRepository = new HostFileRepository("./data/testing/hosts-test.csv");
    Host HOST = hostRepository.findById("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c"); // line 322 in hosts-test.csv


    ReservationRepository repository = new ReservationFileRepository(
            TEST_DIR_PATH,
            new GuestFileRepository("./data/testing/guests-test.csv"),
            new HostFileRepository("./data/testing/hosts-test.csv"));

    @BeforeEach
    void setup() throws IOException {
        Path testPath = Paths.get(TEST_FILE_PATH);
        Path seedPath = Paths.get(SEED_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHost() {
        List<Reservation> reservations = repository.findByHost(HOST);

        assertNotNull(reservations);
        assertEquals(13, reservations.size());
    }
}