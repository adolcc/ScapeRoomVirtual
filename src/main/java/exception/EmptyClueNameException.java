package exception;

public class EmptyClueNameException extends RuntimeException {
    public EmptyClueNameException() {
        super("El nombre de la pista no puede estar vacío.");
    }
}