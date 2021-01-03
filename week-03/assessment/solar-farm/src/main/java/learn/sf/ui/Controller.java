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
                case UPDATE_SECTION:
                    updateSection();
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
        view.printHeader(DisplayMenuOption.DISPLAY_ALL.getMessage());

        List<Panel> panels = service.findAll();
        view.printPanels(panels);
    }

    private void displayPanelsBySection() throws DataAccessException {
        view.printHeader(DisplayMenuOption.DISPLAY_BY_SECTION.getMessage());

        List<String> sections = service.getAllSections();
        String section = view.getSection(sections);

        List<Panel> panels = service.findBySection(section);
        view.printBySection(section, panels);
    }

    private void displayPanelsByMaterial() throws DataAccessException {
        view.printHeader(DisplayMenuOption.DISPLAY_BY_MATERIAL.getMessage());

        PanelMaterial material = view.getPanelMaterial();
        List<Panel> panels = service.findByMaterial(material);

        view.printByMaterial(material, panels);
    }

    private void displayPanelById() throws DataAccessException {
        view.printHeader(DisplayMenuOption.DISPLAY_BY_ID.getMessage());

        int panelId = view.getPanelId();
        Panel panel = service.findById(panelId);

        view.printPanel(panel);
    }

    private void addPanel() throws DataAccessException {
        view.printHeader(MenuOption.ADD.getMessage());

        List<String> sections = service.getAllSections();

        Panel panel = view.getNewPanelToAdd(sections);
        PanelResult result = service.add(panel);

        view.printResult(result, "added");
    }

    private void updatePanel() throws DataAccessException {
        view.printHeader(MenuOption.UPDATE.getMessage());

        List<String> sections = service.getAllSections();
        String section = view.getSection(sections);
        List<Panel> panels = service.findBySection(section);

        view.printBySection(section, panels);

        int panelId = view.getPanelId();
        Panel panel = service.findById(panelId);

        view.printPanel(panel);

        if (panel != null) {
            Panel updatedPanel = view.getUpdatedPanel(panel, sections);

            if (updatedPanel != null) {
                PanelResult result = service.update(updatedPanel);
                view.printResult(result, "updated");
            }
        }
    }

    private void updateSection() throws DataAccessException {
        view.printHeader(MenuOption.UPDATE_SECTION.getMessage());

        List<String> sections = service.getAllSections();
        String section = view.getSection(sections);
        List<Panel> panels = service.findBySection(section);

        view.printHeader(String.format("Move Panels in %s to New Section", section), "*");

        String newSection = view.updateSection(section, sections);

        if (!section.equals(newSection)) {
            for (Panel p : panels) {
                p.setSection(newSection);
                service.update(p);
            }
            view.printPanels(panels);
        }
    }

    private void deletePanel() throws DataAccessException {
        view.printHeader(MenuOption.DELETE.getMessage());

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
