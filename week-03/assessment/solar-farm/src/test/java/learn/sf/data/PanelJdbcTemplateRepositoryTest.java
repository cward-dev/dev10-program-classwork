package learn.sf.data;

import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelJdbcTemplateRepositoryTest {

    private final PanelJdbcTemplateRepository repository;

    public PanelJdbcTemplateRepositoryTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
        repository = context.getBean(PanelJdbcTemplateRepository.class);
    }

    final Panel[] testPanels = new Panel[]{
            new Panel("Bluegrass",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true),
            new Panel("Jazz",54,22,1983,PanelMaterial.AMORPHOUS_SILICON,false),
            new Panel("Gospel",13,15,2005,PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE,true),
            new Panel("Jazz",54,10,2016,PanelMaterial.AMORPHOUS_SILICON,false)
    };

    @BeforeAll
    static void oneTimeSetup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.update("call set_known_good_state();");
    }

    @BeforeEach
    void setupTestPanels() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
//        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
//        jdbcTemplate.update("call set_known_good_state();");
        testPanels[0].setPanelId(1);
        testPanels[1].setPanelId(2);
        testPanels[2].setPanelId(3);
        testPanels[3].setPanelId(4);
    }

    @Test
    void shouldFindAll() {
        List<Panel> panels = repository.findAll();
        Panel[] actual = panels.toArray(new Panel[panels.size()]);

        assertEquals(4, panels.size());
        assertEquals(1, panels.get(0).getPanelId());
        assertArrayEquals(testPanels, actual);
    }

    @Test
    void shouldFindBySection() {
        List<Panel> jazzSectionPanels = repository.findBySection("Jazz");

        assertEquals(2, jazzSectionPanels.size());
    }

    @Test
    void shouldReturnEmptyIfSectionNotPresent() {
        List<Panel> operaSectionPanels = repository.findBySection("Opera");

        assertEquals(0, operaSectionPanels.size());
    }

    @Test
    void shouldFindByMaterialInJazz() {
        List<Panel> amorphousSiliconPanels = repository.findByMaterial(PanelMaterial.AMORPHOUS_SILICON);

        assertEquals(2, amorphousSiliconPanels.size());
    }

    @Test
    void shouldReturnEmptyIfMaterialNotPresent() {
        List<Panel> cadmiumTelluridePanels = repository.findByMaterial(PanelMaterial.CADMIUM_TELLURIDE);

        assertEquals(0, cadmiumTelluridePanels.size());
    }

    @Test
    void shouldFindById() {
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
    void shouldNotFindByInvalidId() {
        Panel panel = repository.findById(100000);

        assertNull(panel);
    }

    @Test
    void shouldFindByLocation() {
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
    void shouldNotFindByInvalidLocation() {
        Panel panel = repository.findByLocation("Bluegrass", 23, 87);

        assertNull(panel);
    }

    @Test
    void shouldGetAllSections() {
        List<String> sections = repository.getAllSections();

        assertEquals(3, sections.size());
    }

    @Test
    void shouldAddNewPanel() {
        Panel newPanel = repository.add(
                new Panel("Bluegrass", 1, 1, 2020, PanelMaterial.AMORPHOUS_SILICON,true));

        assertNotNull(newPanel);
        assertTrue(repository.findAll().size() >= 4);
    }

    @Test
    void shouldUpdatePanel() {
        Panel updatedPanel = new Panel("Jazz",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        updatedPanel.setPanelId(1);
        boolean success = repository.update(updatedPanel);

        assertTrue(success);
        assertTrue(repository.findAll().size() >= 3);
        assertEquals("Jazz", repository.findById(1).getSection());
    }

    @Test
    void shouldNotUpdateNonexistentPanel() {
        Panel updatedPanel = new Panel("Jazz",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        updatedPanel.setPanelId(100000);
        boolean success = repository.update(updatedPanel);

        assertFalse(success);
        assertTrue(repository.findAll().size() >= 3);
        assertNull(repository.findById(100000));
    }

    @Test
    void shouldDeleteById() {
        assertEquals(4, repository.findAll().size());

        boolean success = repository.deleteById(2);

        assertTrue(success);
        assertNull(repository.findById(2));
    }

    @Test
    void shouldNotDeleteByNonexistentId() {
        assertEquals(4, repository.findAll().size());

        boolean success = repository.deleteById(100000);

        assertFalse(success);
        assertTrue(repository.findAll().size() >= 3);
        assertNull(repository.findById(100000));
    }
}