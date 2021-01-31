package learn.field_agent.domain;

import learn.field_agent.data.LoggedExceptionRepository;
import learn.field_agent.models.LoggedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggedExceptionService {

    private final LoggedExceptionRepository repository;

    public LoggedExceptionService(LoggedExceptionRepository repository) {
        this.repository = repository;
    }

    public List<LoggedException> findAll() {
            return repository.findAll();
        }

    public Result<LoggedException> add(LoggedException loggedException) {
        Result<LoggedException> result = validate(loggedException);
        if (!result.isSuccess()) {
            return result;
        }

        if (loggedException.getLoggedExceptionId() != 0) {
            result.addMessage("loggedExceptionId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        loggedException = repository.add(loggedException);
        result.setPayload(loggedException);
        return result;
    }

    private Result<LoggedException> validate(LoggedException loggedException) {
        Result<LoggedException> result = new Result<>();

        if (loggedException == null) {
            result.addMessage("loggedException cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(loggedException.getStatusCode())) {
            result.addMessage("statusCode is required", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(loggedException.getOriginalMessage())) {
            result.addMessage("originalMessage is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(loggedException.getHandledMessage())) {
            result.addMessage("handledMessage is required", ResultType.INVALID);
        }

        if (loggedException.getTimestamp() == null || loggedException.getTimestamp().isAfter(LocalDateTime.now())) {
            result.addMessage("timestamp is required and cannot be in the future", ResultType.INVALID);
        }

        return result;
    }
}
