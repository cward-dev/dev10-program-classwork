package learn.field_agent.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoggedException {

    private int loggedExceptionId;
    private String statusCode;
    private String originalMessage;
    private String displayedMessage;
    private LocalDateTime timestamp;

    public LoggedException() {
    }

    public LoggedException(String statusCode, String originalMessage, String handledMessage, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.originalMessage = originalMessage;
        this.displayedMessage = handledMessage;
        this.timestamp = timestamp;
    }

    public LoggedException(int loggedExceptionId, String statusCode, String originalMessage, String handledMessage, LocalDateTime timestamp) {
        this.loggedExceptionId = loggedExceptionId;
        this.statusCode = statusCode;
        this.originalMessage = originalMessage;
        this.displayedMessage = handledMessage;
        this.timestamp = timestamp;
    }

    public int getLoggedExceptionId() {
        return loggedExceptionId;
    }

    public void setLoggedExceptionId(int loggedExceptionId) {
        this.loggedExceptionId = loggedExceptionId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getDisplayedMessage() {
        return displayedMessage;
    }

    public void setDisplayedMessage(String displayedMessage) {
        this.displayedMessage = displayedMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoggedException that = (LoggedException) o;
        return loggedExceptionId == that.loggedExceptionId
                && Objects.equals(statusCode, that.statusCode)
                && Objects.equals(originalMessage, that.originalMessage)
                && Objects.equals(displayedMessage, that.displayedMessage)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedExceptionId, statusCode, originalMessage, displayedMessage, timestamp);
    }
}
