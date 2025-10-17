package exception;

public class ClueNotFoundException extends RuntimeException {
    public ClueNotFoundException(String message) {
        super(message);
    }
}
