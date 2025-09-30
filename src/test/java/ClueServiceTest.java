import exceptions.DuplicateClueNameException;
import exceptions.EmptyClueNameException;
import exceptions.NullClueNameException;
import models.Clue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClueService;

import static org.junit.Assert.assertNotNull;
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
        clueService.createClue("Mira detrás del cuadro");

        assertTrue(clueService.getClues().contains(new Clue("Mira detrás del cuadro")));
    }

    @Test
    void givenNullName_whenCreatingClue_thenThrowException() {
        Exception e = assertThrows(NullClueNameException.class,
                () -> clueService.createClue(null));

        assertEquals("El nombre de la pista no puede ser nulo.", e.getMessage());
    }

    @Test
    void givenEmptyName_whenCreatingClue_thenThrowException(){
        Exception e = assertThrows(EmptyClueNameException.class,
                () -> clueService.createClue(" "));

        assertEquals("El nombre de la pista no puede estar vacío.", e.getMessage());
    }

    @Test
    void givenAlreadyExistingName_whenCreatingClue_thenThrowException() {
        clueService.createClue("Mira detrás del cuadro");
        Exception e = assertThrows(DuplicateClueNameException.class,
                () -> clueService.createClue("Mira detrás del cuadro"));

        assertEquals("El nombre elegido corresponde a una pista existente.", e.getMessage());
    }

    @Test
    void givenValidInput_WhenCreatingClue_ItPersistsInDatabase() {
        clueService.createClue("Usa la llave roja");

        Clue persisted = clueService.findByName("Usa la llave roja");
        assertNotNull(persisted);
        assertEquals("Usa la llave roja", persisted.getName());
    }
}
