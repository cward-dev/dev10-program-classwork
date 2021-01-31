package learn.field_agent.data;

import learn.field_agent.data.mappers.LoggedExceptionMapper;
import learn.field_agent.models.LoggedException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class LoggedExceptionJdbcTemplateRepository implements LoggedExceptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public LoggedExceptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LoggedException> findAll() {
        final String sql = "select logged_exception_id, original_status_code, handled_status_code, `timestamp`, message "
                + "from logged_exception limit 1000;";
        return jdbcTemplate.query(sql, new LoggedExceptionMapper());
    }

    @Override
    public ResponseEntity<LoggedException> add(LoggedException loggedException) {
        return null;
    }
}
