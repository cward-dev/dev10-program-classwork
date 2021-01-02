package learn.sf.ui;

import learn.sf.data.DataAccessException;
import learn.sf.domain.PanelResult;
import learn.sf.domain.PanelService;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import learn.sf.ui.MenuModel.DisplayMenuOption;
import learn.sf.ui.MenuModel.MenuOption;

import java.util.List;

public class Controller {

    private final PanelService service;
    private final View view;

    public Controller(PanelService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        System.out.println("Welcome to the Solar Farm.");

        try {
            runMenuLoop();
        } catch (DataAccessException ex) {
            System.out.println("CRITICAL ERR: " + ex.getMessage());
        }

        System.out.println();
        System.out.println("Goodbye.");
    }

    private void runMenuLoop() throws DataAccessException {
        MenuOption selection;

        do {
            selection = view.printMenuGetSelection();
            switch (selection) {
                case EXIT:
                    break;
                case DISPLAY:
                    runDisplayMenuLoop();
                    break;
                case ADD:
                    addPanel();
                    break;
                case UPDATE:
                    updatePanel();
                    break;
                case DELETE:
                    deletePanel();
                    break;
            }
        } while (selection != MenuOption.EXIT);
    }

    private void runDisplayMenuLoop() throws DataAccessException {
        DisplayMenuOption selection;

        do {
            selection = view.printDisplayMenuGetSelection();
            switch (selection) {
                case EXIT:
                    System.out.println();
                    System.out.println("Exiting The Display Menu");
                    break;
                case DISPLAY_ALL:
                    displayAllPanels();
                    break;
                case DISPLAY_BY_SECTION:
                    displayPanelsBySection();
                    break;
                case DISPLAY_BY_MATERIAL:
                    displayPanelsByMaterial();
                    break;
                case DISPLAY_BY_ID:
                    displayPanelById();
                    break;
            }
        } while (selection != DisplayMenuOption.EXIT);
    }

    private void displayAllPanels() throws DataAccessException {
        List<Panel> panels = service.findAll();
        view.printAllPanels(panels);
    }

    private void displayPanelsBySection() throws DataAccessException {
        List<String> sections = service.getAllSections();
        String section = view.getSection(sections);

        List<Panel> panels = service.findBySection(section);
        view.printBySection(section, panels);
    }

    private void displayPanelsByMaterial() throws DataAccessException {
        PanelMaterial material = view.getPanelMaterial();
        List<Panel> panels = service.findByMaterial(material);

        view.printByMaterial(material, panels);
    }

    private void displayPanelById() throws DataAccessException {
        int panelId = view.getPanelId();
        Panel panel = service.findById(panelId);

        view.printPanel(panel);
    }

    private void addPanel() throws DataAccessException {
        Panel panel = view.getNewPanelToAdd();
        PanelResult result = service.add(panel);

        view.printResult(result, "added");
    }

    private void updatePanel() throws DataAccessException {
        List<String> sections = service.getAllSections();
        String section = view.getSection(sections);
        List<Panel> panels = service.findBySection(section);

        view.printBySection(section, panels);

        int panelId = view.getPanelId();
        Panel panel = service.findById(panelId);

        view.printPanel(panel);

        if (panel != null) {
            Panel updatedPanel = view.getUpdatedPanel(panel, sections);
            PanelResult result = service.update(updatedPanel);

            view.printResult(result, "updated");
        }
    }

    private void deletePanel() throws DataAccessException {
        List<String> sections = service.getAllSections();
        String section = view.getSection(sections);
        List<Panel> panels = service.findBySection(section);

        view.printBySection(section, panels);

        int panelId = view.getPanelId();
        Panel panel = service.findById(panelId);

        view.printPanel(panel);

        boolean delete = false;
        if (panel != null) {
            delete = view.confirmDeletePanel();
        }

        if (delete) {
            PanelResult result = service.deleteById(panelId);
            view.printResult(result, "deleted");
        }
    }

}
