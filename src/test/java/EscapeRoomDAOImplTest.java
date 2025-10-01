import model.EscapeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.dao.EscapeRoomDAOImpl;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EscapeRoomDAOImplTest {
    private GenericDAO<EscapeRoom, Long> escapeRoomDAO;
    private EscapeRoom escapeRoom;
    private EscapeRoom savedEscapeRoom;

    @BeforeEach
    void setUp() {
        escapeRoomDAO = new EscapeRoomDAOImpl();
        escapeRoom = new EscapeRoom("Ciudad Futura");
        savedEscapeRoom = escapeRoomDAO.save(escapeRoom);
    }

    @Test
    void givenNewEscapeRoom_whenSave_thenShouldPersist() {

        assertNotNull(savedEscapeRoom.getId());
        assertEquals("Ciudad Futura", savedEscapeRoom.getName());
    }

    @Test
    void givenSavedEscapeRoom_whenFindById_thenReturnsCorrectEscapeRoom() {

        Long savedId = savedEscapeRoom.getId();

        Optional<EscapeRoom> found = escapeRoomDAO.findById(savedId);

        assertTrue(found.isPresent());
        assertEquals("Ciudad Futura", found.get().getName());
    }

    @Test
    void givenSavedEscapeRoom_whenFindByName_thenReturnsCorrectEscapeRoom() {
        Optional<EscapeRoom> found = escapeRoomDAO.findByName("Ciudad Futura");

        assertTrue(found.isPresent());
        assertEquals("Ciudad Futura", found.get().getName());
    }

    @Test
    void givenNonExistingId_whenFindById_thenShouldReturnEmpty() {
        Optional<EscapeRoom> found = escapeRoomDAO.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    void givenNonExistingName_whenFindByName_thenShouldReturnEmpty() {
        Optional<EscapeRoom> found = escapeRoomDAO.findByName("No Existe");

        assertFalse(found.isPresent());
    }

    @Test
    void givenMultipleEscapeRooms_whenFindAll_thenShouldReturnAll() {
        escapeRoomDAO.save(new EscapeRoom("La Casa Encantada"));
        escapeRoomDAO.save(new EscapeRoom("El Templo de Orión"));

        List<EscapeRoom> allEscapeRooms = escapeRoomDAO.findAll();

        assertEquals(3, allEscapeRooms.size());

        List<String> names = allEscapeRooms.stream()
                .map(EscapeRoom::getName)
                .collect(Collectors.toList());

        assertTrue(names.contains("Ciudad Futura"));
        assertTrue(names.contains("El Templo de Orión"));
        assertTrue(names.contains("La Casa Encantada"));

    }

    @Test
    void givenEscapeRoom_whenDelete_thenGetsRemovedFromDatabase() {

        escapeRoomDAO.delete(savedEscapeRoom.getId());

        assertFalse(escapeRoomDAO.findById(savedEscapeRoom.getId()).isPresent());
        assertFalse(escapeRoomDAO.findByName("Ciudad Futura").isPresent());
    }

}
