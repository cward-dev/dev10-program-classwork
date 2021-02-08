package learn.sf.data;

import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelFileRepositoryTest {

    static final String TEST_PATH = "./data/solar-panels-test.csv";
    static final String TEST_EMPTY_PATH = "./data/solar-panels-test-empty.csv";
    static final String SEED_PATH = "./data/solar-panels-seed.csv";

    final Panel[] testPanels = new Panel[]{
            new Panel("Bluegrass",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true),
            new Panel("Jazz",54,22,1983,PanelMaterial.AMORPHOUS_SILICON,false),
            new Panel("Gospel",13,15,2005,PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE,true),
            new Panel("Jazz",54,10,2016,PanelMaterial.AMORPHOUS_SILICON,false)
    };

    private final PanelRepository repository = new PanelFileRepository(TEST_PATH);
    private final PanelRepository emptyRepository = new PanelFileRepository(TEST_EMPTY_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

        testPanels[0].setPanelId(1);
        testPanels[1].setPanelId(2);
        testPanels[2].setPanelId(3);
        testPanels[3].setPanelId(4);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Panel> panels = repository.findAll();
        Panel[] actual = panels.toArray(new Panel[panels.size()]);

        assertEquals(4, panels.size());
        assertEquals(1, panels.get(0).getPanelId());
        assertArrayEquals(testPanels, actual);
    }

    @Test
    void shouldReturnEmptyIfNoPanelsExist() throws DataAccessException {
        List<Panel> panels = emptyRepository.findAll();

        assertEquals(0, panels.size());
    }

    @Test
    void shouldFindBySection() throws DataAccessException {
        List<Panel> jazzSectionPanels = repository.findBySection("Jazz");

        assertEquals(2, jazzSectionPanels.size());
    }

    @Test
    void shouldReturnEmptyIfSectionNotPresent() throws DataAccessException {
        List<Panel> operaSectionPanels = repository.findBySection("Opera");

        assertEquals(0, operaSectionPanels.size());
    }

    @Test
    void shouldFindByMaterialInJazz() throws DataAccessException {
        List<Panel> amorphousSiliconPanels = repository.findByMaterial(PanelMaterial.AMORPHOUS_SILICON);

        assertEquals(2, amorphousSiliconPanels.size());
    }

    @Test
    void shouldReturnEmptyIfMaterialNotPresent() throws DataAccessException {
        List<Panel> cadmiumTelluridePanels = repository.findByMaterial(PanelMaterial.CADMIUM_TELLURIDE);

        assertEquals(0, cadmiumTelluridePanels.size());
    }

    @Test
    void shouldFindById() throws DataAccessException {
        Panel panel = repository.findById(1);

        assertEquals(1, panel.getPanelId());
        assertEquals("Bluegrass", panel.getSection());
        assertEquals(3, panel.getRow());
        assertEquals(15, panel.getColumn());
        assertEquals(1994, panel.getYearInstalled());
        assertEquals(PanelMaterial.MULTICRYSTALLINE_SILICON, panel.getMaterial());
        assertTrue(panel.isTracking());
    }

    @Test
    void shouldNotFindByInvalidId() throws DataAccessException {
        Panel panel = repository.findById(100000);

        assertNull(panel);
    }

    @Test
    void shouldFindByLocation() throws DataAccessException {
        Panel panel = repository.findByLocation("Bluegrass", 3, 15);

        assertEquals(1, panel.getPanelId());
        assertEquals("Bluegrass", panel.getSection());
        assertEquals(3, panel.getRow());
        assertEquals(15, panel.getColumn());
        assertEquals(1994, panel.getYearInstalled());
        assertEquals(PanelMaterial.MULTICRYSTALLINE_SILICON, panel.getMaterial());
        assertTrue(panel.isTracking());
    }

    @Test
    void shouldNotFindByInvalidLocation() throws DataAccessException {
        Panel panel = repository.findByLocation("Bluegrass", 23, 87);

        assertNull(panel);
    }

    @Test
    void shouldGetAllSections() throws DataAccessException {
        List<String> sections = repository.getAllSections();

        assertEquals(3, sections.size());
    }

    @Test
    void shouldReturnEmptySectionsIfNoneExist() throws DataAccessException {
        List<String> sections = emptyRepository.getAllSections();

        assertEquals(0, sections.size());
    }

    @Test
    void shouldAddNewPanel() throws DataAccessException {
        List<Panel> panels = repository.findAll();
        assertEquals(4, panels.size());

        Panel newPanel = repository.add(
                new Panel("Bluegrass", 1, 1, 2020, PanelMaterial.AMORPHOUS_SILICON,true));
        panels = repository.findAll();

        assertNotNull(newPanel);
        assertEquals(5, panels.size());
    }

    @Test
    void shouldUpdatePanel() throws DataAccessException {
        Panel updatedPanel = new Panel("Jazz",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        updatedPanel.setPanelId(1);
        boolean success = repository.update(updatedPanel);

        assertTrue(success);
        assertEquals(4,repository.findAll().size());
        assertEquals("Jazz", repository.findById(1).getSection());
    }

    @Test
    void shouldNotUpdateNonexistentPanel() throws DataAccessException {
        Panel updatedPanel = new Panel("Jazz",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        updatedPanel.setPanelId(100000);
        boolean success = repository.update(updatedPanel);

        assertFalse(success);
        assertEquals(4,repository.findAll().size());
        assertNull(repository.findById(100000));
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        assertEquals(4, repository.findAll().size());

        boolean success = repository.deleteById(2);

        assertTrue(success);
        assertEquals(3, repository.findAll().size());
        assertNull(repository.findById(2));
    }

    @Test
    void shouldNotDeleteByNonexistentId() throws DataAccessException {
        assertEquals(4, repository.findAll().size());

        boolean success = repository.deleteById(100000);

        assertFalse(success);
        assertEquals(4, repository.findAll().size());
        assertNull(repository.findById(100000));
    }

    @Test
    void shouldSerializeAndDeserializeStringWithCommaCorrectly() throws DataAccessException {
        Panel panel = repository.add(
                new Panel("Bluegrass, the tester", 1, 1, 2020, PanelMaterial.AMORPHOUS_SILICON,true));

        assertNotNull(panel);
        assertEquals("Bluegrass, the tester", panel.getSection());
    }

    @Test
    void shouldReturnWithCommaInsteadOfDelimiterIfWrittenIntoStringField() throws DataAccessException {
        Panel panel = repository.add( // @@~@@~@@ is the DELIMITER
                new Panel("Bluegrass@@~@@~@@ the tester", 1, 1, 2020, PanelMaterial.AMORPHOUS_SILICON,true));

        assertNotNull(panel);
        assertEquals("Bluegrass, the tester", panel.getSection());
    }
}