package learn.field_agent.data.mappers;

import learn.field_agent.models.LoggedException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggedExceptionMapper implements RowMapper<LoggedException> {

    @Override
    public LoggedException mapRow(ResultSet resultSet, int i) throws SQLException {
        LoggedException loggedException = new LoggedException();
        loggedException.setLoggedExceptionId(resultSet.getInt("logged_exception_id"));
        loggedException.setStatusCode(resultSet.getString("status_code"));
        loggedException.setOriginalMessage(resultSet.getString("original_message"));
        loggedException.setDisplayedMessage(resultSet.getString("displayed_message"));
        loggedException.setTimestamp(resultSet.getTimestamp("exception_timestamp").toLocalDateTime());

        return loggedException;
    }
}
