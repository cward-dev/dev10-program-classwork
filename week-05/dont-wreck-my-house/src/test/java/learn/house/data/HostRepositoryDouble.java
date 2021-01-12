package learn.house.data;

import learn.house.models.Host;

import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    private List<Host> hosts = new ArrayList<>();

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
    public boolean deleteById(String id) throws DataException {
        for (int i = 0; i < hosts.size(); i++) {
            if (id.equals(hosts.get(i).getId())) {
                hosts.remove(i);
                return true;
            }
        }

        return false;
    }
}
