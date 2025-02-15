package learn.house.data;

import learn.house.models.Host;
import learn.house.models.State;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HostFileRepository implements HostRepository {

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;
    private final String DELIMITER = ",";
    private final String DELIMITER_REPLACEMENT = "@@@";

    public HostFileRepository(@Value("${hostsFilePath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

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
    public Host findById(String id) {
        return findAll().stream()
                .filter(host -> host.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(host -> host.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findAllDeleted() {
        ArrayList<Host> result = new ArrayList<>();

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
    public Host findDeletedById(String id) {
        return findAllDeleted().stream()
                .filter(host -> host.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findDeletedByEmail(String email) {
        return findAllDeleted().stream()
                .filter(host -> host.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host add(Host host) throws DataException {
        if (host == null) {
            return null;
        }

        List<Host> all = findAll();

        Host deletedHost = findDeletedByEmail(host.getEmail());
        if (deletedHost != null) {
            host.setId(deletedHost.getId());
        } else {
            host.setId(java.util.UUID.randomUUID().toString());
        }

        String[] fields = serialize(host).split(DELIMITER); // removes DELIMITER_REPLACEMENT and replaces with DELIMITER
        host = deserialize(fields); // TODO research why this is necessary - noticed we used this technique in Sustainable Foraging

        all.add(deserialize(fields));
        writeAll(all);
        removeReactivatedHostFromDeleted(deletedHost);

        return host;
    }

    @Override
    public boolean update(Host host) throws DataException {
        if (host == null || host.getId() == null || host.getId().trim().length() == 0) {
            return false;
        }

        List<Host> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (host.getId().equals(all.get(i).getId())) {
                all.set(i, host);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Host host) throws DataException {
        if (host == null) {
            return false;
        }

        String hostId = host.getId();

        if (hostId == null || hostId.trim().length() == 0) {
            return false;
        }

        List<Host> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (hostId.equals(all.get(i).getId())) {
                moveDeletedHost(all.get(i));
                all.remove(i);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    private void writeAll(List<Host> hosts) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);
            hosts.forEach(host -> writer.println(serialize(host)));

        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private void moveDeletedHost(Host hostToDelete) throws DataException {
        if (hostToDelete == null) {
            return;
        }

        List<Host> allDeleted = findAllDeleted();
        allDeleted.add(hostToDelete);

        try (PrintWriter writer = new PrintWriter(getDeletedFilePath())) {

            writer.println(HEADER);
            allDeleted.forEach(host -> writer.println(serialize(host)));

        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private void removeReactivatedHostFromDeleted(Host hostToRemove) throws DataException {
        if (hostToRemove == null) {
            return;
        }

        List<Host> allDeleted = findAllDeleted();
        allDeleted = allDeleted.stream()
                .filter(g -> !g.getId().equals(hostToRemove.getId()))
                .collect(Collectors.toList());

        try (PrintWriter writer = new PrintWriter(getDeletedFilePath())) {

            writer.println(HEADER);
            allDeleted.forEach(guest -> writer.println(serialize(guest)));

        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                clean(host.getId()),
                clean(host.getLastName()),
                clean(host.getEmail()),
                clean(host.getPhone()),
                clean(host.getAddress()),
                clean(host.getCity()),
                host.getState().getAbbreviation(),
                clean(host.getPostalCode()),
                host.getStandardRate(),
                host.getWeekendRate());
    }

    private Host deserialize(String[] fields) {
        Host host = new Host();

        host.setId(fields[0]);
        host.setLastName(restore(fields[1]));
        host.setEmail(restore(fields[2]));
        host.setPhone(restore(fields[3]));
        host.setAddress(restore(fields[4]));
        host.setCity(restore(fields[5]));
        host.setState(State.getStateFromAbbreviation(fields[6]));
        host.setPostalCode(restore(fields[7]));
        host.setStandardRate(new BigDecimal(fields[8]).setScale(2, RoundingMode.HALF_EVEN));
        host.setWeekendRate(new BigDecimal(fields[9]).setScale(2, RoundingMode.HALF_EVEN));
        return host;
    }

    private String clean(String value) { return value.replace(DELIMITER, DELIMITER_REPLACEMENT); }

    private String restore(String value) { return value.replace(DELIMITER_REPLACEMENT, DELIMITER); }

    private String getDeletedFilePath() {
        return filePath.substring(0, filePath.length() - 4) + "-inactivated.csv";
    }
}
