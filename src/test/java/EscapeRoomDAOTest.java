import daos.EscapeRoomDAO;
import dtos.EscapeRoomDTO;
import exceptions.EscapeRoomNotFoundException;
import implementations.EscapeRoomDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DatabaseSetup;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class EscapeRoomDAOTest {
    private EscapeRoomDAO escapeRoomDAO;
    private DatabaseSetup testDatabase;

    @BeforeEach
    void setUp() throws SQLException {
        testDatabase = new DatabaseSetup();
        testDatabase.initializeDatabase();
        escapeRoomDAO = new EscapeRoomDAOImpl(testDatabase.getConnection());
        testDatabase.cleanDatabase();
    }

    @Test
    void givenNewEscapeRoom_WhenSavingDTO_thenSuccess() {
        EscapeRoomDTO savedDto = escapeRoomDAO.save(new EscapeRoomDTO(null, "Ciudad Futura"));

        assertNotNull(savedDto.getId());
        assertEquals("Ciudad Futura", savedDto.getName());

        assertNotNull(savedDto.getId());
        assertEquals("Ciudad Futura", savedDto.getName());

        EscapeRoomDTO foundDto = escapeRoomDAO.findByName("Ciudad Futura");
        assertNotNull(foundDto);
        assertEquals(savedDto.getId(), foundDto.getId());
    }

    @Test
    void givenNoExistingName_WhenFindingByName_thenThrowException() {
        Exception e = assertThrows(EscapeRoomNotFoundException.class,
                () -> escapeRoomDAO.findByName("No Existe"));
        assertEquals("El Escape Room solicitado no existe.", e.getMessage());
    }

}
