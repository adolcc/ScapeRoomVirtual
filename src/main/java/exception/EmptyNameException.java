package exception;

public class EmptyNameException extends RuntimeException {
    public EmptyNameException() {
        super("El nombre no puede estar vacío.");
    }
}
