package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String directory;
    private final String DELIMITER = ",";
    private final String DELIMITER_REPLACEMENT = "@@@";

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationFileRepository(String directory, GuestRepository guestRepository, HostRepository hostRepository) {
        this.directory = directory;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        if (host == null) {
            return null;
        }

        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host.getId())))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(DELIMITER, -1);

                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException exception) {
            // not throwing on read
        }
        return result;
    }

    @Override
    public Reservation add(Host host, Reservation reservation) throws DataException {

        if (host == null || reservation == null) {
            return null;
        }

        List<Reservation> all = findByHost(host);

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;;

        reservation.setId(nextId);

        String[] fields = serialize(reservation).split(DELIMITER); // removes DELIMITER_REPLACEMENT and replaces with DELIMITER
        reservation = deserialize(fields); // TODO research why this is necessary - noticed we used this technique in Sustainable Foraging

        all.add(deserialize(fields));
        writeAll(host, all);

        return reservation;
    }

    @Override
    public boolean update(Host host, Reservation reservation) throws DataException {

        if (host == null || reservation == null) {
            return false;
        }

        List<Reservation> all = findByHost(host);
        for (int i = 0; i < all.size(); i++) {
            if (reservation.getId() == all.get(i).getId()) {
                all.set(i, reservation);
                writeAll(host, all);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Host host, Reservation reservation) throws DataException {

        if (host == null) {
            return false;
        }

        List<Reservation> all = findByHost(host);
        for (int i = 0; i < all.size(); i++) {
            if (reservation.getId() == all.get(i).getId()) {
                all.remove(i);
                writeAll(host, all);
                return true;
            }
        }

        return false;
    }

    private void writeAll(Host host, List<Reservation> reservations) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host.getId()))) {

            writer.println(HEADER);
            reservations.stream()
                    .sorted(Comparator.comparing(Reservation::getId))
                    .forEach(reservation -> writer.println(serialize(reservation)));

        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields) {
        Reservation reservation = new Reservation();

        reservation.setId(Integer.parseInt(fields[0]));

        String[] startDate = fields[1].split("-"); // { year, month, day }
        reservation.setStartDate(LocalDate.of(
                Integer.parseInt(startDate[0]),
                Integer.parseInt(startDate[1]),
                Integer.parseInt(startDate[2])));

        String[] endDate = fields[2].split("-"); // { year, month, day }
        reservation.setStartDate(LocalDate.of(
                Integer.parseInt(endDate[0]),
                Integer.parseInt(endDate[1]),
                Integer.parseInt(endDate[2])));

        reservation.setGuestId(Integer.parseInt(fields[3]));
        reservation.setTotal(new BigDecimal(fields[4]));
        return reservation;
    }

    private String getFilePath(String hostId) { return Paths.get(directory, hostId + ".csv").toString(); }
}
