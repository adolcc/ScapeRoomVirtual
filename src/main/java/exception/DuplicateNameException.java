package exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException() {
        super("El nombre escogido ya está siendo utilizado.");
    }
}
