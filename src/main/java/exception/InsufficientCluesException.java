package exceptions;


public class InsufficientCluesException extends RuntimeException {
    public InsufficientCluesException() {
        super("La sala debe contener al menos dos pistas.");
    }
}
