package exceptions;

public class EscapeRoomNotFoundException extends RuntimeException {
    public EscapeRoomNotFoundException() {
        super("El Escape Room solicitado no existe.");
    }
}
