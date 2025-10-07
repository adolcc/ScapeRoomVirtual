import exception.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.EscapeRoomService;
import service.RoomService;

import static org.junit.jupiter.api.Assertions.*;

public class RoomServiceTest {

    private EscapeRoomService escapeRoomService;
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        escapeRoomService = new EscapeRoomService();
        roomService = new RoomService();
        escapeRoomService.createEscapeRoom("La Prisión");
    }

    @Test
    void givenValidRoom_whenCreating_thenRoomIsAddedToEscapeRoom() {

        Room room = roomService.createAndValidateRoom("Room Egipcio", 3);
        room.addClue(new Clue("Jeroglífico", 30.0));
        room.addClue(new Clue("Mapa antiguo", 25.0));
        room.addDecoration(new Decoration("Estatua", "Piedra", 50.0));
        room.addDecoration(new Decoration("Antorcha", "Metal", 40.0));

        roomService.validateRoomForEscapeRoom(room);
        escapeRoomService.toString();
        assertTrue(escapeRoomService.getEscapeRoom("La Prisión").get().getRooms().contains(room));
    }

    @Test
    void givenEmptyRoomName_whenCreating_thenThrowEmptyRoomNameException() {
        assertThrows(EmptyRoomNameException.class, () -> {
            roomService.createAndValidateRoom("    ", 2);
        });
    }

    @Test
    void givenDuplicateRoomName_whenCreating_thenThrowDuplicateRoomNameException() {
        roomService.createAndValidateRoom("Room Egipcio", 3);
        DuplicateRoomNameException e = assertThrows(DuplicateRoomNameException.class, () -> {
            roomService.createAndValidateRoom("Room Egipcio", 2);
        });

        assertEquals("Ya existe una sala con ese nombre en el Escape Room.", e.getMessage());
    }

    @Test
    void givenRoomWithLessThanTwoClues_whenValidating_thenThrowInsufficientCluesException() {
        Room room = roomService.createAndValidateRoom("Room Incompleto", 1);
        room.addClue(new Clue("Solo una pista", 10.0));
        room.addDecoration(new Decoration("Cuadro", "Tela", 20.0));
        room.addDecoration(new Decoration("Mesa", "Madera", 30.0));
        Exception e = assertThrows(InsufficientCluesException.class, () -> {
            roomService.addRoomToEscapeRoom("La Prisión", room);
        });

        assertEquals("La sala debe contener al menos dos pistas.", e.getMessage());
    }

    @Test
    void givenRoomWithLessThanTwoDecorations_whenValidating_thenThrowInsufficientDecorationsException() {
        Room room = roomService.createAndValidateRoom("Room Incompleto", 1);
        room.addClue(new Clue("Pista 1", 10.0));
        room.addClue(new Clue("Pista 2", 15.0));
        room.addDecoration(new Decoration("Solo una decoración", "Tela", 20.0));
        assertThrows(InsufficientDecorationsException.class, () -> {
            roomService.validateRoomForEscapeRoom(room);
        });
    }

    @Test
    void givenNullRoomName_whenCreating_thenThrowNullEscapeRoomNameException() {
        NullEscapeRoomNameException e = assertThrows(NullEscapeRoomNameException.class, () -> {
            roomService.createAndValidateRoom(null, 2);
        });

        assertEquals("La sala debe contener al menos dos objetos de decoración.", e.getMessage());
    }
}