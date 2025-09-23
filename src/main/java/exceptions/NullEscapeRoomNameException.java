package exceptions;

public class NullEscapeRoomNameException extends RuntimeException {

    public NullEscapeRoomNameException(){
        super("El nombre del Escape Room no puede ser nulo.");
    }
}
