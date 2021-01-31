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
        final String sql = "select logged_exception_id, status_code, original_message, handled_message, exception_timestamp "
                + "from logged_exception limit 1000;";
        return jdbcTemplate.query(sql, new LoggedExceptionMapper());
    }

    @Override
    public LoggedException add(LoggedException loggedException) {

        final String sql = "insert into logged_exception (status_code, original_message, handled_message, exception_timestamp) "
                + "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, loggedException.getStatusCode());
            ps.setString(2, loggedException.getOriginalMessage());
            ps.setString(3, loggedException.getHandledMessage());
            ps.setTimestamp(4, Timestamp.valueOf(loggedException.getTimestamp()));
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        loggedException.setLoggedExceptionId(keyHolder.getKey().intValue());
        return loggedException;
    }
}
