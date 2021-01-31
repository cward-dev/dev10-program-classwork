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
                new LoggedException(1, 415, 415, LocalDateTime.of(
                        LocalDate.of(1995, 1, 15),
                        LocalTime.of(7, 30, 32)),
                        "Test message"),
                new LoggedException(2, 415, 415, LocalDateTime.of(
                        LocalDate.of(1995, 2, 3),
                        LocalTime.of(17, 4, 53)),
                        "Test message"),
                new LoggedException(3, 415, 415, LocalDateTime.of(
                        LocalDate.of(1995, 2, 13),
                        LocalTime.of(12, 9, 16)),
                        "Test message"));
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
    void shouldNotAddZeroOrNegativeOriginalStatusCode() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setOriginalStatusCode(0);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddZeroOrNegativeHandledStatusCode() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setHandledStatusCode(0);

        Result<LoggedException> actual = service.add(loggedException);
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

    @Test
    void shouldNotAddNullOrBlankMessage() {
        LoggedException loggedException = makeLoggedException();
        loggedException.setMessage(null);

        Result<LoggedException> actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());

        loggedException.setMessage(" ");

        actual = service.add(loggedException);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    private LoggedException makeLoggedException() {
        LoggedException loggedException = new LoggedException();
        loggedException.setOriginalStatusCode(415);
        loggedException.setHandledStatusCode(415);
        loggedException.setTimestamp(LocalDateTime.of(
                    LocalDate.of(1970,1,1),
                    LocalTime.of(7, 30, 12)));
        loggedException.setMessage("Test message");

        return loggedException;
    }
}