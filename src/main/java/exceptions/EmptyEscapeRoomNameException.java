package exceptions;

public class EmptyEscapeRoomNameException extends RuntimeException {

    public EmptyEscapeRoomNameException(){
        super("El nombre del Escape Room no puede estar vacío.");
    }
}
