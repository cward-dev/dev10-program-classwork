package learn.field_agent.models;

import java.time.LocalDateTime;

public class LoggedException {

    private int loggedExceptionId;
    private int originalStatusCode;
    private int handledStatusCode;
    private LocalDateTime timestamp;
    private String message;

    public LoggedException() {
    }

    public LoggedException(int loggedExceptionId, int originalStatusCode, int handledStatusCode, LocalDateTime timestamp, String message) {
        this.loggedExceptionId = loggedExceptionId;
        this.originalStatusCode = originalStatusCode;
        this.handledStatusCode = handledStatusCode;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getLoggedExceptionId() {
        return loggedExceptionId;
    }

    public void setLoggedExceptionId(int loggedExceptionId) {
        this.loggedExceptionId = loggedExceptionId;
    }

    public int getOriginalStatusCode() {
        return originalStatusCode;
    }

    public void setOriginalStatusCode(int originalStatusCode) {
        this.originalStatusCode = originalStatusCode;
    }

    public int getHandledStatusCode() {
        return handledStatusCode;
    }

    public void setHandledStatusCode(int handledStatusCode) {
        this.handledStatusCode = handledStatusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
