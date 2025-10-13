
import model.Clue;
import model.EscapeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.dao.ClueDAOImpl;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClueDAOImplTest {

    private GenericDAO<Clue, Long> clueDAO;
    private Clue clue;
    private Clue savedClue;
    private EscapeRoom escapeRoom;

    @BeforeEach
    void setUp() {
        clueDAO = new ClueDAOImpl();
        escapeRoom = new EscapeRoom("Sala Misteriosa");
        escapeRoom.setId(1L);
        clue = new Clue("Mira detrás del cuadro", 5.0);
        clue.setEscapeRoom(escapeRoom);
        savedClue = clueDAO.save(clue);
    }

    @Test
    void givenNewClue_whenSave_thenShouldPersist() {
        assertNotNull(savedClue.getId());
        assertEquals("Mira detrás del cuadro", savedClue.getName());
        assertEquals(5.0, savedClue.getPrice());
        assertEquals(escapeRoom.getId(), savedClue.getEscapeRoom().getId());
    }

    @Test
    void givenClueWithNegativePrice_whenSave_thenShouldThrowException() {
        Clue invalidClue = new Clue("Pista inválida", -10.0);
        invalidClue.setEscapeRoom(escapeRoom);

        assertThrows(IllegalArgumentException.class, () -> clueDAO.save(invalidClue));
    }

    @Test
    void givenClueWithZeroPrice_whenSave_thenShouldThrowException() {
        Clue invalidClue = new Clue("Pista inválida", 0.0);
        invalidClue.setEscapeRoom(escapeRoom);

        assertThrows(IllegalArgumentException.class, () -> clueDAO.save(invalidClue));
    }

    @Test
    void givenSavedClue_whenFindById_thenReturnsCorrectClue() {
        Optional<Clue> found = clueDAO.findById(savedClue.getId());

        assertTrue(found.isPresent());
        assertEquals("Mira detrás del cuadro", found.get().getName());
        assertEquals(5.0, found.get().getPrice());
        assertEquals(escapeRoom.getId(), found.get().getEscapeRoom().getId());
    }

    @Test
    void givenSavedClue_whenFindByName_thenReturnsCorrectClue() {
        Optional<Clue> found = clueDAO.findByName("Mira detrás del cuadro");

        assertTrue(found.isPresent());
        assertEquals(5.0, found.get().getPrice());
        assertEquals(escapeRoom.getId(), found.get().getEscapeRoom().getId());
    }

    @Test
    void givenNonExistingId_whenFindById_thenReturnsEmpty() {
        Optional<Clue> found = clueDAO.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    void givenNonExistingName_whenFindByName_thenReturnsEmpty() {
        Optional<Clue> found = clueDAO.findByName("Esta pista no existe");

        assertFalse(found.isPresent());
    }

    @Test
    void givenMultipleClues_whenFindAll_thenReturnsAllClues() {
        Clue clue2 = new Clue("Usa la llave roja", 10.0);
        clue2.setEscapeRoom(escapeRoom);
        clueDAO.save(clue2);

        List<Clue> allClues = clueDAO.findAll();

        assertEquals(2, allClues.size());
        assertTrue(allClues.stream().anyMatch(c -> c.getName().equals("Mira detrás del cuadro")));
        assertTrue(allClues.stream().anyMatch(c -> c.getName().equals("Usa la llave roja")));
    }

    @Test
    void givenSavedClue_whenDelete_thenShouldRemoveFromDB() {
        boolean result = clueDAO.delete(savedClue.getId());

        assertTrue(result);
        Optional<Clue> found = clueDAO.findById(savedClue.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void givenNonExistingId_whenDelete_thenReturnsFalse() {
        boolean result = clueDAO.delete(999L);

        assertFalse(result);
    }

    @Test
    void givenExistingClue_whenUpdate_thenShouldUpdateValues() {
        savedClue.setName("Pista actualizada");
        savedClue.setPrice(20.0);

        Clue updated = clueDAO.save(savedClue);

        assertEquals("Pista actualizada", updated.getName());
        assertEquals(20.0, updated.getPrice());

        Optional<Clue> found = clueDAO.findById(savedClue.getId());
        assertTrue(found.isPresent());
        assertEquals("Pista actualizada", found.get().getName());
        assertEquals(20.0, found.get().getPrice());
    }
}
