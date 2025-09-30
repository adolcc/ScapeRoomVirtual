package exception;

public class NullClueNameException extends RuntimeException {
    public NullClueNameException() {
        super("El nombre de la pista no puede ser nulo.");
    }
}