package exceptions;

public class DuplicateRoomNameException extends RuntimeException {
    public DuplicateRoomNameException(String msg) { super(msg); }
}
