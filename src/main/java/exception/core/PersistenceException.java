package exception.core;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String message) {
        super(message);
    }
}