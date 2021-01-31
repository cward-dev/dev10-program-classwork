package learn.field_agent.controllers;

import learn.field_agent.domain.LoggedExceptionService;
import learn.field_agent.models.LoggedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final LoggedExceptionService service;

    public GlobalExceptionHandler(LoggedExceptionService service) {
        this.service = service;
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public ResponseEntity<ErrorResponse> handleException(CannotGetJdbcConnectionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Denied access to database.");

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        service.add(new LoggedException(
                String.valueOf(responseEntity.getStatusCodeValue()),
                ex.getMessage(),
                errorResponse.getMessage(),
                LocalDateTime.now()));

        return responseEntity;
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "We can't show you the details, but something went wrong in our database.");

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        service.add(new LoggedException(
                String.valueOf(responseEntity.getStatusCodeValue()),
                ex.getMessage(),
                errorResponse.getMessage(),
                LocalDateTime.now()));

        return responseEntity;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Illegal Argument: " + ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        service.add(new LoggedException(
                String.valueOf(responseEntity.getStatusCodeValue()),
                ex.getMessage(),
                errorResponse.getMessage(),
                LocalDateTime.now()));

        return responseEntity;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "We couldn't find what you're looking for. Sorry.");

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                errorResponse, HttpStatus.BAD_REQUEST);

        service.add(new LoggedException(
                String.valueOf(responseEntity.getStatusCodeValue()),
                ex.getMessage(),
                errorResponse.getMessage(),
                LocalDateTime.now()));

        return responseEntity;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMediaTypeNotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Unsupported Media Type: " + ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        service.add(new LoggedException(
                String.valueOf(responseEntity.getStatusCodeValue()),
                ex.getMessage(),
                errorResponse.getMessage(),
                LocalDateTime.now()));

        return responseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "These agents appear to have given you the slip. (Something went wrong on our end. Your request failed.)");

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(
                errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        service.add(new LoggedException(
                String.valueOf(responseEntity.getStatusCodeValue()),
                ex.getMessage(),
                errorResponse.getMessage(),
                LocalDateTime.now()));

        return responseEntity;
    }
}
