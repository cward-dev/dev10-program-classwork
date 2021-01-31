package learn.field_agent.data;

import learn.field_agent.models.LoggedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LoggedExceptionJdbcTemplateRepositoryTest {

    final static int NEXT_LOGGED_EXCEPTION_ID = 4;

    @Autowired
    LoggedExceptionJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<LoggedException> loggedExceptions = repository.findAll();

        assertTrue(loggedExceptions.size() > 0);
    }

    @Test
    void shouldAdd() {
        LoggedException loggedException = makeLoggedException();
        LoggedException actual = repository.add(loggedException);
        assertNotNull(actual);
        assertEquals(NEXT_LOGGED_EXCEPTION_ID, actual.getLoggedExceptionId());
    }

    private LoggedException makeLoggedException() {
        LoggedException loggedException = new LoggedException();
        loggedException.setStatusCode("499");
        loggedException.setOriginalMessage("Original Message");
        loggedException.setDisplayedMessage("Displayed Message");
        loggedException.setTimestamp(LocalDateTime.of(
                    LocalDate.of(1970,1,1),
                    LocalTime.of(7, 30, 12)));

        return loggedException;
    }
}