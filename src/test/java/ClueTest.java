import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClueTest {

    // criterios de aceptación
    //Las salas tendrán pistas específicas.

    @Test
    public void givenRoom_whenGetCLue_thenReturnSpecificClue() {

        Room sala = new Room("Jurassic Park");
        assertEquals("Jurassic Park", sala.getPista());

    }

    @Test
    public void givenRoomWithDifferentClue_whenGetClue_thenReturnCorrectClue() {
        Room sala = new Room("Star Wars");
        assertEquals("Star Wars", sala.getPista());

    }

    @Test
    public void givenRoomWithEmptyClue_whenCreateRoom_thenThrowException () {
        Exception exception = assertThrows(RoomEmptyException.class, () -> {
            new Room("");
        });
        assertEquals("The room cannot be empty", exception.getMessage());
    }

}