package learn.unexplained.ui;

import learn.unexplained.data.DataAccessException;
import learn.unexplained.domain.EncounterResult;
import learn.unexplained.domain.EncounterService;
import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import java.util.List;

public class Controller {

    private final EncounterService service;
    private final View view;

    public Controller(EncounterService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        view.printHeader("Welcome To Unexplained Encounters.");

        try {
            runMenuLoop();
        } catch (DataAccessException ex) {
            view.printHeader("CRITICAL ERROR:" + ex.getMessage());
        }

        view.printHeader("Goodbye");
    }

    private void runMenuLoop() throws DataAccessException {
        MenuOption option;
        do {
            option = view.displayMenuGetOption();
            switch (option) {
                case DISPLAY_ALL:
                    displayAllEncounters();
                    break;
                case DISPLAY_BY_TYPE:
                    displayEncountersByType();
                    break;
                case ADD:
                    addEncounter();
                    break;
                case UPDATE:
                    updateEncounter();
                    break;
                case DELETE:
                    deleteEncounter();
                    break;
            }
        } while (option != MenuOption.EXIT);
    }

    private void displayAllEncounters() throws DataAccessException {
        List<Encounter> encounters = service.findAll();
        view.printAllEncounters(encounters);
    }

    private void displayEncountersByType() throws DataAccessException {
        EncounterType type = view.getEncounterType();

        List<Encounter> encounters = service.findByType(type);

        view.printEncountersOfType(encounters);
    }

    private void addEncounter() throws DataAccessException {
        Encounter encounter = view.makeEncounter();
        EncounterResult result = service.add(encounter);
        view.printResult(result);
    }

    private void updateEncounter() throws DataAccessException {
        int encounterId = view.getEncounterId();

        Encounter encounter = service.findById(encounterId);
        view.printEncounter(encounter);

        Encounter updatedEncounter = null;
        if (encounter != null) {
            updatedEncounter = view.getUpdatedEncounter(encounter);
        }

        EncounterResult result = service.update(updatedEncounter);
        view.printResult(result);
    }

    private void deleteEncounter() throws DataAccessException {
        int encounterId = view.getEncounterIdToDelete();
        Encounter encounter = service.findById(encounterId);

        view.printEncounter(encounter);

        boolean delete = false;
        if (encounter != null) {
            delete = view.confirmDeleteEncounter();
        }

        if (delete) {
            EncounterResult result = service.deleteById(encounterId);
            view.printResult(result);
        }
    }

}
