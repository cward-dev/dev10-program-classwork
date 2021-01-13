package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.State;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuestFileRepository implements GuestRepository {

    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;
    private final String DELIMITER = ",";
    private final String DELIMITER_REPLACEMENT = "@@@";

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(DELIMITER, -1);

                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException exception) {
            // not throwing on read
        }
        return result;
    }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(guest -> guest.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findByEmail(String email) {
        return findAll().stream()
                .filter(guest -> guest.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findAllDeleted() {
        ArrayList<Guest> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getDeletedFilePath()))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(DELIMITER, -1);

                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException exception) {
            // not throwing on read
        }
        return result;
    }

    @Override
    public Guest findDeletedById(int id) {
        return findAllDeleted().stream()
                .filter(guest -> guest.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findDeletedByEmail(String email) {
        return findAllDeleted().stream()
                .filter(guest -> guest.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest add(Guest guest) throws DataException {

        if (guest == null) {
            return null;
        }

        List<Guest> all = findAll();

        Guest deletedGuest = findDeletedByEmail(guest.getEmail());
        if (deletedGuest!= null) {
            guest.setId(deletedGuest.getId()); // TODO need to remove deleted guest from "deleted" file
        } else {
            int nextId = all.stream()
                    .mapToInt(Guest::getId)
                    .max()
                    .orElse(0) + 1;
            guest.setId(nextId);
        }

        String[] fields = serialize(guest).split(DELIMITER); // removes DELIMITER_REPLACEMENT and replaces with DELIMITER
        guest = deserialize(fields); // TODO research why this is necessary - noticed we used this technique in Sustainable Foraging

        all.add(deserialize(fields));
        writeAll(all);

        return guest;
    }

    @Override
    public boolean update(Guest guest) throws DataException {

        if (guest == null) {
            return false;
        }

        List<Guest> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (guest.getId() == all.get(i).getId()) {
                all.set(i, guest);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataException {

        List<Guest> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (id == all.get(i).getId()) {
                moveDeletedGuest(all.get(i));
                all.remove(i);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    private void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);
            guests.stream()
                    .sorted(Comparator.comparing(Guest::getId))
                    .forEach(guest -> writer.println(serialize(guest)));

        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private void moveDeletedGuest(Guest guestToDelete) throws DataException {
        if (guestToDelete == null) {
            return;
        }

        List<Guest> allDeleted = findAllDeleted();
        allDeleted.add(guestToDelete);

        try (PrintWriter writer = new PrintWriter(getDeletedFilePath())) {

            writer.println(HEADER);
            allDeleted.forEach(guest -> writer.println(serialize(guest)));

        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getId(),
                clean(guest.getFirstName()),
                clean(guest.getLastName()),
                clean(guest.getEmail()),
                clean(guest.getPhone()),
                guest.getState().getAbbreviation());
    }

    private Guest deserialize(String[] fields) {
        Guest guest = new Guest();

        guest.setId(Integer.parseInt(fields[0]));
        guest.setFirstName(restore(fields[1]));
        guest.setLastName(restore(fields[2]));
        guest.setEmail(restore(fields[3]));
        guest.setPhone(restore(fields[4]));
        guest.setState(State.getStateFromAbbreviation(fields[5]));

        return guest;
    }

    private String clean(String value) { return value.replace(DELIMITER, DELIMITER_REPLACEMENT); }

    private String restore(String value) { return value.replace(DELIMITER_REPLACEMENT, DELIMITER); }

    private String getDeletedFilePath() {
        return filePath.substring(0, filePath.length() - 4) + "-deleted.csv";
    }
}
