import exception.core.DuplicateResourceException;
import exception.core.ValidationException;
import model.Clue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClueService;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClueServiceTest {

    private ClueService clueService;

    @BeforeEach
    void setUp() {
        clueService = new ClueService();
    }

    @Test
    void givenValidName_whenCreatingClue_thenSuccess() {
        Clue clue = clueService.createClue("Mira detrás del placard.", 15);

        assertTrue(clueService.getClues().contains(clue));
    }

    @Test
    void givenNullName_whenCreatingClue_thenThrowException() {
        Exception e = assertThrows(ValidationException.class,
                () -> clueService.createClue(null, 0));

        assertEquals("El campo 'nombre' es obligatorio.", e.getMessage());
    }

    @Test
    void givenEmptyName_whenCreatingClue_thenThrowException(){
        Exception e = assertThrows(ValidationException.class,
                () -> clueService.createClue(" ", 0));

        assertEquals("El campo 'nombre' es obligatorio.", e.getMessage());
    }

    @Test
    void givenAlreadyExistingName_whenCreatingClue_thenThrowException() {
        Exception e = assertThrows(DuplicateResourceException.class,
                () -> clueService.createClue("Mira detrás del cuadro", 500));

        assertEquals("Ya existe Pista 'Mira detrás del cuadro'.", e.getMessage());
    }
}
