package learn.field_agent.data;


import learn.field_agent.controllers.ErrorResponse;
import learn.field_agent.models.LoggedException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoggedExceptionRepository {
    List<LoggedException> findAll();

    LoggedException add(LoggedException loggedException);
}
