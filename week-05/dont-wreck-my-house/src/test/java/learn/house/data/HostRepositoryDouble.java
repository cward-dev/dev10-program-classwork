package learn.house.data;

import learn.house.models.Host;
import learn.house.models.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    private List<Host> hosts = new ArrayList<>();
    private List<Host> hostsInactivated = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
                "Yearnes",
                "eyearnes0@sfgate.com",
                "(806) 1783815",
                "3 Nova Trail", "Amarillo", State.TEXAS, "79182",
                new BigDecimal("340.00"),
                new BigDecimal("425.00")));

        hosts.add(new Host("a0d911e7-4fde-4e4a-bdb7-f047f15615e8",
                "Rhodes",
                "krhodes1@posterous.com",
                "(478) 7475991",
                "7262 Morning Avenue", "Macon", State.GEORGIA, "31296",
                new BigDecimal("295.00"),
                new BigDecimal("368.75")));

        hosts.add(new Host("b4f38829-c663-48fc-8bf3-7fca47a7ae70",
                "Fader",
                "mfader2@amazon.co.jp",
                "(501) 2490895",
                "99208 Morning Parkway", "North Little Rock", State.ARIZONA, "72118",
                new BigDecimal("451.00"),
                new BigDecimal("563.75")));

        hosts.add(new Host("9f2578e7-6723-482b-97c1-f9be0b7c96dd",
                "Spellesy",
                "rspellesy3@google.co.jp",
                "(214) 5201692",
                "78765 Lotheville Drive", "Garland", State.TEXAS, "75044",
                new BigDecimal("433.00"),
                new BigDecimal("541.25")));

        hosts.add(new Host("b6ddb844-b990-471a-8c0a-519d0777eb9b",
                "Harley",
                "charley4@apple.com",
                "(954) 7895760",
                "1 Maple Wood Terrace", "Orlando", State.FLORIDA, "32825",
                new BigDecimal("176.00"),
                new BigDecimal("220.00")));
    }

    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findById(String id) {
        return hosts.stream()
                .filter(host -> host.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findByEmail(String email) {
        return hosts.stream()
                .filter(host -> host.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findAllDeleted() {
        return hostsInactivated;
    }

    @Override
    public Host findDeletedById(String id) {
        return hostsInactivated.stream()
                .filter(host -> host.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findDeletedByEmail(String email) {
        return hostsInactivated.stream()
                .filter(host -> host.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host add(Host host) throws DataException {
        host.setId(java.util.UUID.randomUUID().toString());
        hosts.add(host);
        return host;
    }

    @Override
    public boolean update(Host host) throws DataException {
        for (int i = 0; i < hosts.size(); i++) {
            if (host.getId().equals(hosts.get(i).getId())) {
                hosts.set(i, host);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Host host) throws DataException {
        for (int i = 0; i < hosts.size(); i++) {
            if (host.getId().equals(hosts.get(i).getId())) {
                hostsInactivated.add(hosts.get(i));
                hosts.remove(i);
                return true;
            }
        }

        return false;
    }
}
