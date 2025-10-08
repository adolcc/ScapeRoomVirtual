package exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException() {
        super("Sala inexistente.");
    }
}
