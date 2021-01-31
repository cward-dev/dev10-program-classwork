package learn.field_agent.data;

import learn.field_agent.data.mappers.AgencyAgentMapper;
import learn.field_agent.data.mappers.AgencyMapper;
import learn.field_agent.models.Agency;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgencyAgentJdbcTemplateRepository implements AgencyAgentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AgencyAgentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AgencyAgent> findBySecurityClearanceId(int securityClearanceId) {
        final String sql = "select "
                + "aa.agency_id as agency_id, "
                + "aa.identifier as identifier, "
                + "aa.activation_date as activation_date, "
                + "aa.is_active as is_active, "
                + "sc.security_clearance_id as security_clearance_id, "
                + "sc.`name` as security_clearance_name, "
                + "a.agent_id as agent_id, "
                + "a.first_name as first_name, "
                + "a.middle_name as middle_name, "
                + "a.last_name as last_name, "
                + "a.dob as dob, "
                + "a.height_in_inches as height_in_inches "
                + "from agency_agent aa "
                + "inner join security_clearance sc on aa.security_clearance_id = sc.security_clearance_id "
                + "inner join agent a on aa.agent_id = a.agent_id "
                + "where sc.security_clearance_id = ?;";

        return jdbcTemplate.query(
                sql, new AgencyAgentMapper(), securityClearanceId);
    }

    @Override
    public boolean add(AgencyAgent agencyAgent) {

        final String sql = "insert into agency_agent (agency_id, agent_id, identifier, security_clearance_id, "
                + "activation_date, is_active) values "
                + "(?,?,?,?,?,?);";

        return jdbcTemplate.update(sql,
                agencyAgent.getAgencyId(),
                agencyAgent.getAgent().getAgentId(),
                agencyAgent.getIdentifier(),
                agencyAgent.getSecurityClearance().getSecurityClearanceId(),
                agencyAgent.getActivationDate(),
                agencyAgent.isActive()) > 0;
    }

    @Override
    public boolean update(AgencyAgent agencyAgent) {

        final String sql = "update agency_agent set "
                + "identifier = ?, "
                + "security_clearance_id = ?, "
                + "activation_date = ?, "
                + "is_active = ? "
                + "where agency_id = ? and agent_id = ?;";

        return jdbcTemplate.update(sql,
                agencyAgent.getIdentifier(),
                agencyAgent.getSecurityClearance().getSecurityClearanceId(),
                agencyAgent.getActivationDate(),
                agencyAgent.isActive(),
                agencyAgent.getAgencyId(),
                agencyAgent.getAgent().getAgentId()) > 0;

    }

    @Override
    public boolean deleteByKey(int agencyId, int agentId) {

        final String sql = "delete from agency_agent "
                + "where agency_id = ? and agent_id = ?;";

        return jdbcTemplate.update(sql, agencyId, agentId) > 0;
    }
}
