package learn.memories.ui;

import learn.memories.data.DataAccessException;
import learn.memories.domain.MemoryResult;
import learn.memories.domain.MemoryService;
import learn.memories.models.Memory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private View view;

    @Autowired // direct field injection
    private final MemoryService service;

    @Autowired // indicates both a view and service should be injected.
    public Controller(View view, MemoryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        view.displayHeader("Welcome to Memories.");
        try {
            runApp();
        } catch (DataAccessException ex) {
            view.displayErrors(List.of(ex.getMessage()));
        }
        view.displayMessage("Goodbye.");
    }

    private void runApp() throws DataAccessException {

        for (int option = view.chooseMenuOption();
             option > 0;
             option = view.chooseMenuOption()) {

            switch (option) {
                case 1:
                    viewMemories();
                    break;
                case 2:
                    addMemory();
                    break;
                case 3:
                    updateMemory();
                    break;
                case 4:
                    deleteMemory();
                    break;
            }
        }
    }

    private void viewMemories() throws DataAccessException {
        List<Memory> memories = getMemories("View Memories");
        view.displayMemories(memories);
    }

    private void addMemory() throws DataAccessException {
        Memory m = view.createMemory();
        MemoryResult result = service.add(m);
        if (result.isSuccess()) {
            view.displayMessage("Memory " + result.getMemory().getId() + " created.");
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }

    private void updateMemory() throws DataAccessException {
        List<Memory> memories = getMemories("Update a Memory");
        Memory m = view.chooseMemory(memories);
        if (m == null) {
            view.displayMessage("Memory not found.");
            return;
        }
        m = view.editMemory(m);
        MemoryResult result = service.update(m);
        if (result.isSuccess()) {
            view.displayMessage("Memory " + result.getMemory().getId() + " updated.");
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }

    private void deleteMemory() throws DataAccessException {
        List<Memory> memories = getMemories("Delete a Memory");
        Memory m = view.chooseMemory(memories);
        if (m != null && service.deleteById(m.getId()).isSuccess()) {
            view.displayMessage("Memory " + m.getId() + " deleted.");
        } else {
            view.displayMessage("Memory not found.");
        }
    }

    private List<Memory> getMemories(String title) throws DataAccessException {
        view.displayHeader(title);
        if (view.isPublic()) {
            return service.findPublicMemories();
        }
        return service.findPrivateMemories();

    }

    @Autowired // setter injection
    private void setView(View view) {
        this.view = view;
    }
}
