import service.DAO;
import service.DTO;
import exception.EscapeRoomNotFoundException;
import implementation.DAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import database.DatabaseSetup;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class EscapeRoomDAOTest {
    private DAO DAO;
    private DatabaseSetup testDatabase;

    @BeforeEach
    void setUp() throws SQLException {
        testDatabase = new DatabaseSetup();
        testDatabase.initializeDatabase();
        DAO = new DAOImpl(testDatabase.getConnection());
        testDatabase.cleanDatabase();
    }

    @Test
    void givenNewEscapeRoom_WhenSavingDTO_thenSuccess() {
        DTO savedDto = DAO.save(new DTO(null, "Ciudad Futura"));

        assertNotNull(savedDto.getId());
        assertEquals("Ciudad Futura", savedDto.getName());

        assertNotNull(savedDto.getId());
        assertEquals("Ciudad Futura", savedDto.getName());

        DTO foundDto = DAO.findByName("Ciudad Futura");
        assertNotNull(foundDto);
        assertEquals(savedDto.getId(), foundDto.getId());
    }

    @Test
    void givenNoExistingName_WhenFindingByName_thenThrowException() {
        Exception e = assertThrows(EscapeRoomNotFoundException.class,
                () -> DAO.findByName("No Existe"));
        assertEquals("El Escape Room solicitado no existe.", e.getMessage());
    }

}
