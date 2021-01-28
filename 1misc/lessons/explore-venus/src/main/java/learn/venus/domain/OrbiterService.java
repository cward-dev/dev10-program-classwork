package learn.venus.domain;

import learn.venus.data.DataAccessException;
import learn.venus.data.OrbiterRepository;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrbiterService {

    private final OrbiterRepository repository;

    public OrbiterService(OrbiterRepository repository) {
        this.repository = repository;
    }

    public List<Orbiter> findByType(OrbiterType type) throws DataAccessException {
        return repository.findByType(type);
    }

    // add
    // can't be null
    // name required
    // module == 4 astronauts
    // module with dock == 2 astro, 1 shuttle

    public OrbiterResult add(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = validateInputs(orbiter);
        if (!result.isSuccess()) {
            return result;
        }

        Map<OrbiterType, Integer> counts = countTypes();
        counts.put(orbiter.getType(), counts.get(orbiter.getType()) + 1);
        result = validateDomain(counts);
        if (!result.isSuccess()) {
            return result;
        }

        Orbiter o = repository.add(orbiter);
        result.setPayload(o);

        return result;
    }

    public OrbiterResult update(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = validateInputs(orbiter);
        if (!result.isSuccess()) {
            return result;
        }

        Orbiter existing = repository.findById(orbiter.getOrbiterId());
        if (existing == null) {
            result.addErrorMessage("Orbiter Id " + orbiter.getOrbiterId() + " not found.");
            return result;
        }

        if (existing.getType() != orbiter.getType()) {
            result.addErrorMessage("Cannot update type.");
            return result;
        }

        boolean success = repository.update(orbiter);
        if (!success) {
            result.addErrorMessage("Could not find Orbiter Id " + orbiter.getOrbiterId());
        }

        return result;
    }

    public OrbiterResult deleteById(int orbiterId) throws DataAccessException {
        OrbiterResult result = new OrbiterResult();

        Orbiter orbiter = repository.findById(orbiterId);
        if (orbiter == null) {
            result.addErrorMessage("Could not find Orbiter Id " + orbiter.getOrbiterId());
            return result;
        }

        Map<OrbiterType, Integer> counts = countTypes();
        counts.put(orbiter.getType(), counts.get(orbiter.getType()) - 1);

        result = validateDomain(counts);
        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.deleteById(orbiterId);
        if (!success) {
            result.addErrorMessage("Could not find Orbiter Id " + orbiter.getOrbiterId());
        }

        return result;

    }

    public OrbiterResult validateInputs(Orbiter orbiter) {
        OrbiterResult result = new OrbiterResult();
        if (orbiter == null) {
            result.addErrorMessage("orbiter cannot be null");
            return result;
        }

        if(orbiter.getName() == null || orbiter.getName().trim().length() == 0) {
            result.addErrorMessage("name is required.");
        }
        return result;
    }

    private Map<OrbiterType, Integer> countTypes() {

        HashMap<OrbiterType, Integer> counts = new HashMap<>();
        counts.put(OrbiterType.MODULE, 0);
        counts.put(OrbiterType.MODULE_WITH_DOCK, 0);
        counts.put(OrbiterType.ASTRONAUT, 0);
        counts.put(OrbiterType.SHUTTLE, 0);
        counts.put(OrbiterType.VENUSIAN, 0);

        try {
            List<Orbiter> allOrbiters = repository.findAll();
            for (Orbiter o : allOrbiters) {
                switch (o.getType()) {
                    case MODULE:
                        counts.put(OrbiterType.MODULE, counts.get(OrbiterType.MODULE) + 1);
                        break;
                    case MODULE_WITH_DOCK:
                        counts.put(OrbiterType.MODULE_WITH_DOCK, counts.get(OrbiterType.MODULE_WITH_DOCK) + 1);
                        break;
                    case ASTRONAUT:
                        counts.put(OrbiterType.ASTRONAUT, counts.get(OrbiterType.ASTRONAUT) + 1);
                        break;
                    case SHUTTLE:
                        counts.put(OrbiterType.SHUTTLE, counts.get(OrbiterType.SHUTTLE) + 1);
                        break;
                    case VENUSIAN:
                        counts.put(OrbiterType.VENUSIAN, counts.get(OrbiterType.VENUSIAN) + 1);
                        break;
                }
            }
        } catch (DataAccessException ex) {
            // ignore for now?
        }

        return counts;
    }

    private OrbiterResult validateDomain(Map<OrbiterType, Integer> counts) {

        int astroCount = counts.get(OrbiterType.ASTRONAUT);
        int modCount = counts.get(OrbiterType.MODULE);
        int dockCount = counts.get(OrbiterType.MODULE_WITH_DOCK);
        int shuttleCount = counts.get(OrbiterType.SHUTTLE);

        OrbiterResult result = new OrbiterResult();

        if (astroCount > modCount * 4 + dockCount * 2) {
            result.addErrorMessage("No room for an astronaut.");
        }

        if (shuttleCount > dockCount) {
            result.addErrorMessage("No room for a shuttle.");
        }

        return result;
    }

}
