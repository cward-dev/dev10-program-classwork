package learn.unexplained.data;

import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EncounterFileRepository implements EncounterRepository {

    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private static final String HEADER = "encounter_id,type,when,description,occurrences";
    private final String filePath;

    public EncounterFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Encounter> findAll() throws DataAccessException {

        ArrayList<Encounter> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // skip header
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Encounter encounter = deserialize(line);
                if (encounter != null) {
                    result.add(encounter);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("That file was not found.");
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public Encounter findById(int encounterId) throws DataAccessException {
        List<Encounter> encounters = findAll();
        for (Encounter e : encounters) {
            if (e.getEncounterId() == encounterId) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Encounter> findByType(EncounterType encounterType) throws DataAccessException {
        List<Encounter> encounters = findAll();

        ArrayList<Encounter> result = new ArrayList<>();
        for (Encounter e : encounters) {
            if (e.getType() == encounterType) {
                result.add(e);
            }
        }

        return result;
    }

    @Override
    public Encounter add(Encounter encounter) throws DataAccessException {
        List<Encounter> encounters = findAll();

        encounter.setEncounterId(getNextId(encounters));
        encounters.add(encounter);
        writeAll(encounters);
        return encounter;
    }

    @Override
    public boolean update(Encounter encounter) throws DataAccessException {
        List<Encounter> encounters = findAll();

        for (int i = 0; i < encounters.size(); i++) {
            if (encounters.get(i).getEncounterId() == encounter.getEncounterId()) {
                encounters.remove(i);
                encounters.add(encounter);
                writeAll(encounters);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int encounterId) throws DataAccessException {
        List<Encounter> encounters = findAll();
        for (int i = 0; i < encounters.size(); i++) {
            if (encounters.get(i).getEncounterId() == encounterId) {
                encounters.remove(i);
                writeAll(encounters);
                return true;
            }
        }
        return false;
    }

    private int getNextId(List<Encounter> allEncounters) {
        int nextId = 0;
        for (Encounter e : allEncounters) {
            nextId = Math.max(nextId, e.getEncounterId());
        }
        return nextId + 1;
    }

    private void writeAll(List<Encounter> encounters) throws DataAccessException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);
            for (Encounter e : encounters) {
                writer.println(serialize(e));
            }
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }

    private String serialize(Encounter encounter) {
        return String.format("%s,%s,%s,%s,%s",
                encounter.getEncounterId(),
                encounter.getType(),
                clean(encounter.getWhen()),
                clean(encounter.getDescription()),
                encounter.getOccurrences());
    }

    private Encounter deserialize(String line) {
        String[] fields = line.split(DELIMITER, -1);
        if (fields.length == 5) {
            Encounter encounter = new Encounter();
            encounter.setEncounterId(Integer.parseInt(fields[0]));
            encounter.setType(EncounterType.valueOf(fields[1]));
            encounter.setWhen(restore(fields[2]));
            encounter.setDescription(restore(fields[3]));
            encounter.setOccurrences(Integer.parseInt(fields[4]));
            return encounter;
        }
        return null;
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

}
