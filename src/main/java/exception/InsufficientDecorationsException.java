package exceptions;

public class InsufficientDecorationsException extends RuntimeException {
    public InsufficientDecorationsException() {
        super("La sala debe contener al menos dos objetos de decoración.");
    }
}

