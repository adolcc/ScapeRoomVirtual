package exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
        super("El precio no puede ser nulo o negativo.");
    }
}
