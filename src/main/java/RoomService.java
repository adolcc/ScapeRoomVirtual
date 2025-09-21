import java.util.List;

public class RoomService {
    private List<EscapeRoom> availableRooms;

    private RoomService(List<EscapeRoom> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public static RoomService createRoomService(List<EscapeRoom> rooms) {
        return new RoomService(rooms);
    }

    public Room createRoom() {

        return new Room(availableRooms.get(0));
    }
}
