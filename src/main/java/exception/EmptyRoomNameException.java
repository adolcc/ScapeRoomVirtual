package exceptions;

public class EmptyRoomNameException extends RuntimeException {
    public EmptyRoomNameException() {
        super("El nombre de la sala no puede estar vacío.");
    }
}


