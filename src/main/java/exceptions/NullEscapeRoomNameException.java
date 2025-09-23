package exceptions;

public class NullEscapeRoomNameException extends RuntimeException {

    public NullEscapeRoomNameException(String s){
        super("El nombre del Escape Room no puede ser nulo.");
    }
}
