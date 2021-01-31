package learn.field_agent.data;

import learn.field_agent.data.mappers.LoggedExceptionMapper;
import learn.field_agent.models.LoggedException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class LoggedExceptionJdbcTemplateRepository implements LoggedExceptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public LoggedExceptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LoggedException> findAll() {
        final String sql = "select logged_exception_id, original_status_code, handled_status_code, exception_timestamp, message "
                + "from logged_exception limit 1000;";
        return jdbcTemplate.query(sql, new LoggedExceptionMapper());
    }

    @Override
    public LoggedException add(LoggedException loggedException) {

        final String sql = "insert into logged_exception (original_status_code, handled_status_code, exception_timestamp, message)"
                + "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, loggedException.getOriginalStatusCode());
            ps.setInt(2, loggedException.getHandledStatusCode());
            ps.setTimestamp(3, Timestamp.valueOf(loggedException.getTimestamp()));
            ps.setString(4, loggedException.getMessage());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        loggedException.setLoggedExceptionId(keyHolder.getKey().intValue());
        return loggedException;
    }
}
