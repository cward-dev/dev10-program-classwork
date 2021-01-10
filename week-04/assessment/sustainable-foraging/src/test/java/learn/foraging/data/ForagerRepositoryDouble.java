package learn.foraging.data;

import learn.foraging.models.Forager;
import learn.foraging.models.State;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ForagerRepositoryDouble implements ForagerRepository {

    public final static Forager FORAGER = makeForager();

    private final ArrayList<Forager> foragers = new ArrayList<>();

    public ForagerRepositoryDouble() {
        foragers.add(FORAGER);
    }

    @Override
    public Forager add(Forager forager) {
        foragers.add(forager);
        return forager;
    }

    @Override
    public Forager findById(String id) {
        return foragers.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Forager> findAll() {
        return foragers;
    }

    @Override
    public List<Forager> findByState(String state) {
        if (state != null && state.trim().length() == 2) {
            return foragers.stream()
                    .filter(i -> i.getState().getAbbreviation().equals(state))
                    .collect(Collectors.toList());
        }

        return foragers.stream()
                .filter(i -> i.getState().equals(State.getStateFromAbbreviation(state)))
                .collect(Collectors.toList());
    }

    private static Forager makeForager() {
        Forager forager = new Forager();
        forager.setId("0e4707f4-407e-4ec9-9665-baca0aabe88c");
        forager.setFirstName("Jilly");
        forager.setLastName("Sisse");
        forager.setState(State.getStateFromAbbreviation("GA"));
        return forager;
    }

}
