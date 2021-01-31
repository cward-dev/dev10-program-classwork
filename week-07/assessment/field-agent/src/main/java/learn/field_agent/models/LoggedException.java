package learn.field_agent.models;

import java.time.LocalDateTime;

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
}
