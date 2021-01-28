package learn.venus.ui;

import learn.venus.data.DataAccessException;
import learn.venus.domain.OrbiterResult;
import learn.venus.domain.OrbiterService;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.List;

public class Controller {

    private final OrbiterService service;
    private final View view;

    public Controller(OrbiterService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            runMenu();
        } catch (DataAccessException ex) {
            view.printHeader("Fatal Err: " + ex);
        }
    }

    private void runMenu() throws DataAccessException {
        MenuOption option;
        do {
            option = view.displayMenuAndSelect();
            System.out.println(option.getTitle());
            switch (option) {
                case EXIT:
                    view.printHeader("Goodbye.");
                    break;
                case DISPLAY_ORBITERS:
                    displayOrbiters();
                    break;
                case CREATE_ORBITER:
                    createOrbiter();
                    break;
                case UPDATE_ORBITER:
                    updateOrbiter();
                    break;
                case DELETE_ORBITER:
                    deleteOrbiter();
                    break;
            }
        } while (option != MenuOption.EXIT);
    }

    private void displayOrbiters() throws DataAccessException {
        view.printHeader(MenuOption.DISPLAY_ORBITERS.getTitle());
        OrbiterType type = view.readOrbiterType();
        List<Orbiter> orbiters = service.findByType(type);
        view.displayOrbiters(orbiters);
    }

    private void createOrbiter() throws DataAccessException {
        view.printHeader(MenuOption.CREATE_ORBITER.getTitle());
        Orbiter orbiter = view.makeOrbiter();
        OrbiterResult result = service.add(orbiter);
        view.displayResult(result);
    }

    private void updateOrbiter() throws DataAccessException {
        view.printHeader(MenuOption.UPDATE_ORBITER.getTitle());

        OrbiterType type = view.readOrbiterType();
        List<Orbiter> orbiters = service.findByType(type);

        Orbiter orbiter = view.update(orbiters);
        if (orbiter == null) {
            return;
        }

        OrbiterResult result = service.update(orbiter);
        view.displayResult(result);
    }

    private void deleteOrbiter() throws DataAccessException {
        view.printHeader(MenuOption.DELETE_ORBITER.getTitle());

        OrbiterType type = view.readOrbiterType();
        List<Orbiter> orbiters = service.findByType(type);

        Orbiter orbiter = view.findOrbiter(orbiters);
        if (orbiter == null) {
            return;
        }

        OrbiterResult result = service.deleteById(orbiter.getOrbiterId());
        view.displayResult(result);
    }
}
