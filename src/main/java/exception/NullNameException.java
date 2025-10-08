package exception;

public class NullNameException extends RuntimeException {
    public NullNameException() {
        super("El nombre no puede ser nulo.");
    }
}
