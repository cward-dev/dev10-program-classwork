package learn.foraging.data;

import learn.foraging.models.Forager;
import learn.foraging.models.Item;
import learn.foraging.models.State;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ForagerFileRepository implements ForagerRepository {

    private static final String HEADER = "id,first_name,last_name,state";
    private final String filePath;

    private final String DELIMITER = ",";
    private final String DELIMITER_REPLACEMENT = "@@@";

    public ForagerFileRepository(@Value("${foragersFilePath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Forager> findAll() {
        ArrayList<Forager> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER, -1);
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
                .filter(i -> i.getState().getAbbreviation().equalsIgnoreCase(stateAbbr))
                .collect(Collectors.toList());
    }

    @Override
    public Forager add(Forager forager) throws DataException {
        List<Forager> all = findAll();
        forager.setId((java.util.UUID.randomUUID().toString()));

        String[] fields = serialize(forager).split(DELIMITER); // removes DELIMITER_REPLACEMENT and replaces with DELIMITER
        forager = deserialize(fields);

        all.add(forager);
        writeAll(all);
        return forager;
    }

    @Override
    public boolean update(Forager forager) throws DataException {

        if (forager == null) {
            return false;
        }

        List<Forager> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (forager.getId().equals(all.get(i).getId())) {
                all.set(i, forager);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    private void writeAll(List<Forager> all) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);

            for (Forager forager : all) {
                writer.println(serialize(forager));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Forager forager) {
        return String.format("%s,%s,%s,%s",
                clean(forager.getId()),
                clean(forager.getFirstName()),
                clean(forager.getLastName()),
                clean(forager.getState().getAbbreviation())
                );
    }

    private Forager deserialize(String[] fields) {
        Forager result = new Forager();
        result.setId(restore(fields[0]));
        result.setFirstName(restore(fields[1]));
        result.setLastName(restore(fields[2]));
        result.setState(State.getStateFromAbbreviation(restore(fields[3])));
        return result;
    }

    private String clean(String value) { return value.replace(DELIMITER, DELIMITER_REPLACEMENT); }

    private String restore(String value) { return value.replace(DELIMITER_REPLACEMENT, DELIMITER); }
}
