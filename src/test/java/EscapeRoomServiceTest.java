import exception.DuplicateEscapeRoomNameException;
import exception.EmptyEscapeRoomNameException;
import exception.NullEscapeRoomNameException;
import model.EscapeRoom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.database.DatabaseSetup;
import service.EscapeRoomService;

import java.sql.SQLException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EscapeRoomServiceTest {

    private EscapeRoomService escapeRoomService;
    private DatabaseSetup dbSetup;

    @BeforeEach
    void setUp() throws SQLException {
        dbSetup = new DatabaseSetup();
        dbSetup.cleanDatabase();

        escapeRoomService = new EscapeRoomService();
    }

    @AfterEach
    void tearDown() throws SQLException {
        dbSetup.cleanDatabase();
    }

    @Test
    void givenValidName_whenCreatingEscapeRoom_thenSuccess() {
        escapeRoomService.createEscapeRoom("La Prisión");

        assertTrue(escapeRoomService.getEscapeRooms().contains(new EscapeRoom("La Prisión")));
    }

    @Test
    void givenNullName_whenCreatingEscapeRoom_thenThrowException() {
        Exception e = assertThrows(NullEscapeRoomNameException.class,
                () -> escapeRoomService.createEscapeRoom(null));

        assertEquals("El nombre del Escape Room no puede ser nulo.", e.getMessage());
    }

    @Test
    void givenEmptyName_whenCreatingEscapeRoom_thenThrowException(){
        Exception e = assertThrows(EmptyEscapeRoomNameException.class,
                () -> escapeRoomService.createEscapeRoom(" "));

        assertEquals("El nombre del Escape Room no puede estar vacío.", e.getMessage());
    }

    @Test
    void givenAlreadyExistingName_whenCreatingEscapeRoom_thenThrowException() {
        escapeRoomService.createEscapeRoom("La Prisión");
        Exception e = assertThrows(DuplicateEscapeRoomNameException.class,
        () -> escapeRoomService.createEscapeRoom("La Prisión"));

        assertEquals("El nombre elegido corresponde a un Escape Room existente.", e.getMessage());
    }
}