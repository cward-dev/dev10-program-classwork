package learn.sf.data;

public class DataAccessException extends Exception {

    public DataAccessException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
}
