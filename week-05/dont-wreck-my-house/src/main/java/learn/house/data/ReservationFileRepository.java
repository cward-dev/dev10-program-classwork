package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;
    private final String DELIMITER = ",";

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationFileRepository(@Value("${reservationsDirPath}") String directory,
                                     GuestRepository guestRepository,
                                     HostRepository hostRepository) {
        this.directory = directory;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        ArrayList<Reservation> result = new ArrayList<>();

        if (host == null) {
            return result;
        }

        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(Host::getId, h -> h));
            hostMap.putAll(hostRepository.findAllDeleted().stream()
                .collect(Collectors.toMap(Host::getId, h -> h)));

        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(Guest::getId, h -> h));
            guestMap.putAll(guestRepository.findAllDeleted().stream()
                .collect(Collectors.toMap(Guest::getId, g -> g)));

        Reservation reservation;

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host.getId())))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(DELIMITER, -1);

                if (fields.length == 5) {
                    reservation = deserialize(fields, host.getId());
                    reservation.setHost(hostMap.get(reservation.getHost().getId()));
                    reservation.setGuest(guestMap.get(reservation.getGuest().getId()));
                    result.add(reservation);
                }
            }
        } catch (IOException exception) {
            // not throwing on read
        }
        return result;
    }

    @Override
    public List<Reservation> findAll() {
        List<Host> hosts = hostRepository.findAll();
        hosts.addAll(hostRepository.findAllDeleted());

        ArrayList<Reservation> result = new ArrayList<>();
        hosts.forEach(host -> result.addAll(findByHost(host)));

        return result;
    }

    @Override
    public List<Reservation> findByGuest(Guest guest) {
        return findAll().stream()
                .filter(reservation -> reservation.getGuest().equals(guest))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {

        if (reservation == null || reservation.getHost() == null) {
            return null;
        }

        List<Reservation> all = findByHost(reservation.getHost());

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;;

        reservation.setId(nextId);

        String[] fields = serialize(reservation).split(DELIMITER); // removes DELIMITER_REPLACEMENT and replaces with DELIMITER
        reservation = deserialize(fields, reservation.getHost().getId()); // TODO research why this is necessary - noticed we used this technique in Sustainable Foraging

        all.add(deserialize(fields, reservation.getHost().getId()));
        writeAll(all, reservation.getHost());

        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {

        if (reservation == null || reservation.getHost() == null
                || reservation.getHost().getId() == null || reservation.getHost().getId().trim().length() == 0) {
            return false;
        }

        List<Reservation> all = findByHost(reservation.getHost());
        for (int i = 0; i < all.size(); i++) {
            if (reservation.getId() == all.get(i).getId()) {
                all.set(i, reservation);
                writeAll(all, reservation.getHost());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {

        if (reservation == null || reservation.getHost() == null
                || reservation.getHost().getId() == null || reservation.getHost().getId().trim().length() == 0) {
            return false;
        }

        List<Reservation> all = findByHost(reservation.getHost());
        for (int i = 0; i < all.size(); i++) {
            if (reservation.getId() == all.get(i).getId()) {
                all.remove(i);
                writeAll(all, reservation.getHost());
                return true;
            }
        }

        return false;
    }

    private void writeAll(List<Reservation> reservations, Host host) throws DataException {
        if (reservations.size() == 0) {
            try {
                Files.delete(Paths.get(getFilePath(host.getId())));
            } catch (IOException exception) {
                // if host reservations file not found, then no need to delete
            }
            return;
        }

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
                reservation.getGuest().getId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String hostId) {
        Reservation reservation = new Reservation();

        reservation.setId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        reservation.setHost(new Host());
        reservation.getHost().setId(hostId);
        reservation.setGuest(new Guest());
        reservation.getGuest().setId(Integer.parseInt(fields[3]));

        reservation.setTotal(new BigDecimal(fields[4]).setScale(2, RoundingMode.HALF_EVEN));
        return reservation;
    }

    private String getFilePath(String hostId) { return Paths.get(directory, hostId + ".csv").toString(); }
}
