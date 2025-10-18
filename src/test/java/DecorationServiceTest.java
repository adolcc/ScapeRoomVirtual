import exception.*;
import model.Decoration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import repository.database.DatabaseSetup;
import service.DecorationService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.List;

public class DecorationServiceTest {

    private DecorationService decorationService;
    private DatabaseSetup dbSetup;

    @BeforeEach
    void setUp() throws SQLException {
        dbSetup = new DatabaseSetup();
        dbSetup.cleanDatabase();
        decorationService = new DecorationService();
    }

    @AfterEach
    void tearDown() throws SQLException {
        dbSetup.cleanDatabase();
    }

    @Test
    void givenValidData_whenCreatingDecoration_thenSuccess() {

        Decoration decoration = decorationService.createDecoration("Cuaderno de Registro", "cartón y papel", 21.12);

        assertAll("Verificar atributos de la decoración.",
                () -> assertNotNull(decoration, "El objeto creado es nulo."),
                () -> assertEquals("Cuaderno de Registro", decoration.getName(), "El nombre no coincide."),
                () -> assertEquals("cartón y papel", decoration.getMaterial(), "El material no coincide."),
                () -> assertEquals(21.12, decoration.getPrice(), "El precio no coincide."),
                () -> assertNull(decoration.getRoomId(), "roomId debe ser nulo."),
                () -> assertTrue(decorationService.getDecorations().contains(decoration), "La deco no está en la lista.")
        );
    }

    @Test
    void givenMultipleValidDecorations_whenCreatingDeco_thenAllAreStored() {
        Decoration decoration1 = decorationService.createDecoration("Caja Oculta", "metal", 53.74);
        Decoration decoration2 = decorationService.createDecoration("Cuaderno de Registro", "cartón y papel", 21.12);

        List<Decoration> decorations = decorationService.getDecorations();

        assertEquals(2, decorations.size());
        assertTrue(decorations.contains(decoration1));
        assertTrue(decorations.contains(decoration2));
    }

    @Test
    void givenNullName_whenCreatingDecoration_thenThrowException() {

        Exception e = assertThrows(NullNameException.class,
                () -> decorationService.createDecoration(null, "plástico", 17.71));

        assertEquals("El nombre no puede ser nulo.", e.getMessage());
    }

    @Test
    void givenEmptyName_whenCreatingDecoration_thenThrowException() {

        Exception e = assertThrows(EmptyNameException.class,
                () -> decorationService.createDecoration(" ", "plástico", 17.71));

        assertEquals("El nombre no puede estar vacío.", e.getMessage());
    }

    @Test
    void givenNullMaterial_whenCreatingDecoration_thenThrowException() {
        Exception e = assertThrows(NullNameException.class,
                () -> decorationService.createDecoration("Caja Oculta", null, 53.74));


        assertEquals("El nombre no puede ser nulo.", e.getMessage());
    }

    @Test
    void givenEmptyMaterial_whenCreatingDecoration_thenThrowException() {
        Exception e = assertThrows(EmptyNameException.class,
                () -> decorationService.createDecoration("Caja Oculta", " ", 53.74));

        assertEquals("El nombre no puede estar vacío.", e.getMessage());
    }

    @Test
    void givenZeroPrice_whenCreatingDecoration_thenThrowException() {
        Exception e = assertThrows(InvalidPriceException.class,
                () -> decorationService.createDecoration("Cuaderno de Registro", "cartón y papel", 0.0));

        assertEquals("El precio no puede ser nulo o negativo.", e.getMessage());
    }

    @Test
    void givenNegativePrice_whenCreatingDecoration_thenThrowException() {
        Exception e = assertThrows(InvalidPriceException.class,
                () -> decorationService.createDecoration("Cuaderno de Registro", "cartón y papel", -22.33));

        assertEquals("El precio no puede ser nulo o negativo.", e.getMessage());
    }

    @Test
    void givenDuplicateName_whenCreatingDecoration_thenThrowException() {
        decorationService.createDecoration("Cuadro Sospechoso", "madera y tela", 55.77);

        Exception e = assertThrows(DuplicateNameException.class,
                () -> decorationService.createDecoration("Cuadro sospechoso", "tela y madera", 77.55));

        assertEquals("El nombre escogido ya está siendo utilizado.", e.getMessage());
    }
}