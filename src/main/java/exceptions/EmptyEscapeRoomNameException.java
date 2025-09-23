package exceptions;

public class EmptyEscapeRoomNameException extends RuntimeException {

    public EmptyEscapeRoomNameException(String s){
        super("El nombre del Escape Room no puede estar vacío.");
    }
}
