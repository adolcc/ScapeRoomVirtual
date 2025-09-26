package exceptions;

public class DuplicateRoomNameException extends RuntimeException {
    public DuplicateRoomNameException() {
        super("Ya existe una sala con ese nombre en el Escape Room.");
    }
}
