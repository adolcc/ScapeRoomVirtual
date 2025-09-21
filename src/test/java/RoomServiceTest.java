import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
public class RoomServiceTest {

    private RoomService roomService;
    private EscapeRoom escapeRoomAvailable;

    @BeforeEach
    public void setUp() {
        escapeRoomAvailable = new EscapeRoom("La Prisión");
        roomService = RoomService.createRoomService(List.of(escapeRoomAvailable));
    }

    @Test
    public void shouldAssignScapeRoomWhenCreatingNewRoom() {
        Room newRoom = roomService.createRoom();
        Assertions.assertNotNull(newRoom);
        Assertions.assertNotNull(newRoom.getScapeRoom());
        Assertions.assertEquals(escapeRoomAvailable, newRoom.getScapeRoom());
    }
}