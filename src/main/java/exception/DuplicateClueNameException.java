package exception;

public class DuplicateClueNameException extends RuntimeException {
    public DuplicateClueNameException() {
        super("El nombre elegido corresponde a una pista existente.");
    }
}