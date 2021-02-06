package learn.field_agent.data.mappers;

import learn.field_agent.models.Mission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MissionMapper implements RowMapper<Mission> {

    @Override
    public Mission mapRow(ResultSet resultSet, int i) throws SQLException {
        Mission mission = new Mission();
        mission.setMissionId(resultSet.getInt("mission_id"));
        mission.setCodeName(resultSet.getString("code_name"));
        mission.setNotes(resultSet.getString("notes"));
        mission.setStartDate(resultSet.getDate("start_date").toLocalDate());
        mission.setProjectedEndDate(resultSet.getDate("projected_end_date").toLocalDate());
        mission.setActualEndDate(resultSet.getDate("actual_end_date").toLocalDate());
        mission.setOperationalCost(resultSet.getBigDecimal("operational_cost"));
        mission.setAgencyId(resultSet.getInt("agency_id"));

        return mission;
    }
}