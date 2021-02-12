package learn.field_agent.data;

import learn.field_agent.models.Mission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MissionRepository {

    List<Mission> findAll();

    List<Mission> findByAgencyId(int agencyId);

    Mission findById(int missionId);

    Mission add(Mission mission);

    boolean update(Mission mission);

    boolean deleteById(int missionId);
}
