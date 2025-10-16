package exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String s) {
        super("Sala inexistente.");
    }
}
