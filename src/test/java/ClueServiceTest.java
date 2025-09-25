import exceptions.DuplicateRoomClueException;
import exceptions.EmptyRoomClueException;
import exceptions.NullRoomClueException;
import models.Room;
import org.junit.jupiter.api.Test;
import services.RoomService;

import static org.junit.jupiter.api.Assertions.*;

public class ClueServiceTest {

    // criterios de aceptación
    //Las salas tendrán pistas específicas.
    @Test
    public void givenRoom_whenGetClue_thenReturnSpecificClue() {

        Room room = new Room("Jurassic Park");
        assertEquals("Jurassic Park", room.getClue());

    }

    @Test
    public void givenRoomWithEmptyClue_whenCreateRoom_thenThrowException() {
        Exception e = assertThrows(EmptyRoomClueException.class, () -> {
            new Room("");
        });
        assertEquals("La sala debe tener al menos una pista", e.getMessage());
    }

    @Test
    public void givenRoomWithNullClue_whenCreateRoom_thenThrowException() {
        Exception e = assertThrows(NullRoomClueException.class, () -> {
            new Room(null);
        });
        assertEquals("La pista no puede ser nula", e.getMessage());
    }

    @Test
    public void givenDuplicateClue_whenAddClue_thenThrowException() {
        RoomService roomService = new RoomService();
        roomService.addClue("Jurassic Park");

        Exception e = assertThrows(DuplicateRoomClueException.class, () -> {
            roomService.addClue("Jurassic Park");
        });
        assertEquals("La pista ya existe", e.getMessage());
    }

}