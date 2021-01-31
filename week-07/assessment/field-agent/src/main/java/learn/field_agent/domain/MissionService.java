package learn.field_agent.domain;

import learn.field_agent.data.AgencyRepository;
import learn.field_agent.data.MissionRepository;
import learn.field_agent.models.Mission;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MissionService {

    private final MissionRepository repository;

    private final AgencyRepository agencyRepository;

    public MissionService(MissionRepository repository, AgencyRepository agencyRepository) {
        this.repository = repository;
        this.agencyRepository = agencyRepository;
    }

    public List<Mission> findAll() {
        return repository.findAll();
    }

    public List<Mission> findByAgencyId(int agencyId) {
        return repository.findByAgencyId(agencyId);
    }

    public Mission findById(int missionId) {
        return repository.findById(missionId);
    }

    public Result<Mission> add(Mission mission) {
        Result<Mission> result = validate(mission);
        if (!result.isSuccess()) {
            return result;
        }

        if (mission.getMissionId() != 0) {
            result.addMessage("missionId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        mission = repository.add(mission);
        result.setPayload(mission);
        return result;
    }

    public Result<Mission> update(Mission mission) {
        Result<Mission> result = validate(mission);
        if (!result.isSuccess()) {
            return result;
        }

        if (mission.getMissionId() <= 0) {
            result.addMessage("missionId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(mission)) {
            String msg = String.format("missionId: %s, not found", mission.getMissionId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int missionId) {
        return repository.deleteById(missionId);
    }

    private Result<Mission> validate(Mission mission) {
        Result<Mission> result = new Result<>();

        if (mission == null) {
            result.addMessage("mission cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(mission.getCodeName())) {
            result.addMessage("codeName is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(mission.getNotes())) { // make notes null if blank
            mission.setNotes(null);
        }

        if (mission.getStartDate() == null) {
            result.addMessage("startDate is required", ResultType.INVALID);
        } else {
            if (mission.getProjectedEndDate() == null) {
                result.addMessage("projectedEndDate is required", ResultType.INVALID);
            } else if (mission.getProjectedEndDate().isBefore(mission.getStartDate())) {
                result.addMessage("projectedEndDate cannot be before startDate", ResultType.INVALID);
            }

            if (mission.getActualEndDate() == null) {
                result.addMessage("actualEndDate is required", ResultType.INVALID);
            } else if (mission.getActualEndDate().isBefore(mission.getStartDate())) {
                result.addMessage("actualEndDate cannot be before startDate", ResultType.INVALID);
            }
        }

        if (mission.getOperationalCost() == null) {
            result.addMessage("operationalCost is required", ResultType.INVALID);
        } else if (mission.getOperationalCost().compareTo(new BigDecimal("0.00")) < 0) {
            result.addMessage("operationalCost cannot be a negative amount", ResultType.INVALID);
        }

        if (!checkForValidAgencyId(mission.getAgencyId())) {
            String msg = String.format("agencyId: %s, not found", mission.getAgencyId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        if (checkForDuplicate(mission)) {
            String msg = String.format("codeName: '%s', agencyId: %s, already exists",
                    mission.getCodeName(), mission.getAgencyId());
            result.addMessage(msg, ResultType.INVALID);
        }

        return result;
    }

    private boolean checkForDuplicate(Mission mission) {
        if (mission.getCodeName() == null) {
            return false;
        }
        return findAll().stream()
                .filter(m -> m.getCodeName().equalsIgnoreCase(mission.getCodeName()))
                .anyMatch(m -> m.getAgencyId() == mission.getAgencyId()
                        && m.getMissionId() != mission.getMissionId());
    }

    private boolean checkForValidAgencyId(int agencyId) {
        return agencyRepository.findById(agencyId) != null;
    }
}
