package learn.foraging.data;

import learn.foraging.models.Forager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ForagerFileRepository implements ForagerRepository {

    private final String filePath;

    private final String DELIMITER = ",";
    private final String DELIMITER_REPLACEMENT = "@@@";

    public ForagerFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Forager> findAll() {
        ArrayList<Forager> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 4) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Forager findById(String id) {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Forager> findByState(String stateAbbr) {
        return findAll().stream()
                .filter(i -> i.getState().equalsIgnoreCase(stateAbbr))
                .collect(Collectors.toList());
    }
    
    private Forager deserialize(String[] fields) {
        Forager result = new Forager();
        result.setId(restore(fields[0]));
        result.setFirstName(restore(fields[1]));
        result.setLastName(restore(fields[2]));
        result.setState(restore(fields[3]));
        return result;
    }

    private String clean(String value) { return value.replace(DELIMITER, DELIMITER_REPLACEMENT); } // TODO will use for serialize method when we add the "add a forager" feature

    private String restore(String value) { return value.replace(DELIMITER_REPLACEMENT, DELIMITER); }
}
