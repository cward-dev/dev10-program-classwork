package learn.sf.data;

import learn.sf.domain.PanelResult;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelFileRepositoryTest {

    static final String TEST_PATH = ".data/solar-panels-test.csv";
    static final String SEED_PATH = ".data/solar-panels-seed.csv";

    private PanelRepository repository = new PanelFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Panel> panels = repository.findAll();

        assertEquals(4, panels.size());
        assertEquals(1, panels.get(1).getPanelId());
    }

}