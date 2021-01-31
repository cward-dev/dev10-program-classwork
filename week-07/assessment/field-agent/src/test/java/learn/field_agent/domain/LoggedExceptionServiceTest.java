package learn.field_agent.domain;

import learn.field_agent.data.LoggedExceptionRepository;
import learn.field_agent.models.LoggedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LoggedExceptionServiceTest {

    @Autowired
    LoggedExceptionService service;

    @MockBean
    LoggedExceptionRepository repository;

    @Test
    void shouldFindAll() {
        // pass-through test, probably not useful
        List<LoggedException> expected = List.of(
                new LoggedException(1, "499", "Test Original", "Test Handled", LocalDateTime.of(
                        LocalDate.of(1995, 1, 15),
                        LocalTime.of(7, 30, 32))),
                new LoggedException(2, "499", "Test Original", "Test Handled", LocalDateTime.of(
                        LocalDate.of(1995, 2, 3),
                        LocalTime.of(17, 4, 53))),
                new LoggedException(3, "499", "Test Original", "Test Handled", LocalDateTime.of(
                        LocalDate.of(1995, 2, 13),
                        LocalTime.of(12, 9, 16))));
        when(repository.findAll()).thenReturn(expected);
        List<LoggedException> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        LoggedException loggedException = makeLoggedException();
        LoggedException mockOut = makeLoggedException();
        mockOut.setLoggedExceptionId(1);

        when(repository.add(loggedException)).thenReturn(mockOut);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<LoggedException> actual = service.add(null);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankStatusCode() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setStatusCode(null);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());

        loggedException.setStatusCode(" ");

        actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankOriginalMessage() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setOriginalMessage(null);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());

        loggedException.setOriginalMessage(" ");

        actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankHandledMessage() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setHandledMessage(null);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());

        loggedException.setHandledMessage(" ");

        actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullOrFutureTimestamp() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setTimestamp(null);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());

        loggedException.setTimestamp(LocalDateTime.of(LocalDate.now().plusYears(1), LocalTime.of(12, 30)));

        actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    private LoggedException makeLoggedException() {
        LoggedException loggedException = new LoggedException();
        loggedException.setStatusCode("499");
        loggedException.setOriginalMessage("Original Message");
        loggedException.setHandledMessage("Handled Message");
        loggedException.setTimestamp(LocalDateTime.of(
                    LocalDate.of(1970,1,1),
                    LocalTime.of(7, 30, 12)));

        return loggedException;
    }
}