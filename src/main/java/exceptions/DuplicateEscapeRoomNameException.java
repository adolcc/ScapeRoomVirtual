package exceptions;

public class DuplicateEscapeRoomNameException extends RuntimeException {

    public DuplicateEscapeRoomNameException() {
        super("El nombre elegido corresponde a un Escape Room existente.");
    }
}
