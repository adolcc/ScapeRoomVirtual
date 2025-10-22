import exception.core.DuplicateResourceException;
import exception.core.ValidationException;
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
        Exception e = assertThrows(ValidationException.class,
                () -> escapeRoomService.createEscapeRoom(null));

        assertEquals("El campo 'nombre' es obligatorio.", e.getMessage());
    }

    @Test
    void givenEmptyName_whenCreatingEscapeRoom_thenThrowException(){
        Exception e = assertThrows(ValidationException.class,
                () -> escapeRoomService.createEscapeRoom(" "));

        assertEquals("El campo 'nombre' es obligatorio.", e.getMessage());
    }

    @Test
    void givenAlreadyExistingName_whenCreatingEscapeRoom_thenThrowException() {
        escapeRoomService.createEscapeRoom("La Prisión");
        Exception e = assertThrows(DuplicateResourceException.class,
        () -> escapeRoomService.createEscapeRoom("La Prisión"));

        assertEquals("Ya existe Escape Room 'La Prisión'.", e.getMessage());
    }
}