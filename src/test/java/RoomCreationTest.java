import exceptions.*;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.EscapeRoomService;

import static org.junit.jupiter.api.Assertions.*;

public class RoomCreationTest {

    private EscapeRoomService escapeRoomService;

    @BeforeEach
    void setUp() {
        escapeRoomService = new EscapeRoomService();
        escapeRoomService.createEscapeRoom("La Prisión");
    }

    @Test
    void givenValidRoom_whenCreating_thenRoomIsAddedToEscapeRoom() {
        Room room = new Room("Room Egipcio", 3);
        room.addClue(new Clue("Jeroglífico", 30.0));
        room.addClue(new Clue("Mapa antiguo", 25.0));
        room.addDecoration(new Decoration("Estatua", "Piedra", 50.0));
        room.addDecoration(new Decoration("Antorcha", "Metal", 40.0));

        escapeRoomService.addRoomToEscapeRoom("La Prisión", room);

        assertTrue(escapeRoomService.getEscapeRoom("La Prisión").getRooms().contains(room));
    }

    @Test
    void givenEmptyRoomName_whenCreating_thenThrowEmptyRoomNameException() {

            Exception e = assertThrows(EmptyRoomNameException.class, () -> {
                new Room("    ", 2);
            });

            assertEquals("El nombre de la sala no puede estar vacío.", e.getMessage());
        }

    @Test
    void givenDuplicateRoomName_whenCreating_thenThrowDuplicateRoomNameException() {
        Room room1 = new Room("Room Egipcio", 3);
        room1.addClue(new Clue("Pista 1", 10.0));
        room1.addClue(new Clue("Pista 2", 15.0));
        room1.addDecoration(new Decoration("Cuadro", "Tela", 20.0));
        room1.addDecoration(new Decoration("Mesa", "Madera", 30.0));
        escapeRoomService.addRoomToEscapeRoom("La Prisión", room1);

        Room room2 = new Room("Room Egipcio", 2);
        room2.addClue(new Clue("Pista A", 12.0));
        room2.addClue(new Clue("Pista B", 18.0));
        room2.addDecoration(new Decoration("Lámpara", "Cristal", 25.0));
        room2.addDecoration(new Decoration("Silla", "Metal", 35.0));

        Exception e = assertThrows(DuplicateRoomNameException.class, () -> {
            escapeRoomService.addRoomToEscapeRoom("La Prisión", room2);
        });

        assertEquals("Ya existe una sala con ese nombre en el Escape Room.", e.getMessage());
    }

    @Test
    void givenRoomWithLessThanTwoClues_whenCreating_thenThrowInsufficientCluesException() {
        Room room = new Room("Room Incompleto", 1);
        room.addClue(new Clue("Solo una pista", 10.0));
        room.addDecoration(new Decoration("Cuadro", "Tela", 20.0));
        room.addDecoration(new Decoration("Mesa", "Madera", 30.0));

        Exception e = assertThrows(InsufficientCluesException.class, () -> {
            escapeRoomService.addRoomToEscapeRoom("La Prisión", room);
        });

        assertEquals("La sala debe contener al menos dos pistas.", e.getMessage());
    }

    @Test
    void givenRoomWithLessThanTwoDecorations_whenCreating_thenThrowInsufficientDecorationsException() {
        Room room = new Room("Room Incompleto", 1);
        room.addClue(new Clue("Pista 1", 10.0));
        room.addClue(new Clue("Pista 2", 15.0));
        room.addDecoration(new Decoration("Solo una decoración", "Tela", 20.0));

        Exception e = assertThrows(InsufficientDecorationsException.class, () -> {
            escapeRoomService.addRoomToEscapeRoom("La Prisión", room);
        });

        assertEquals("La sala debe contener al menos dos objetos de decoración.", e.getMessage());
    }
}