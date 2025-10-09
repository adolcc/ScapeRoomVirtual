import exception.DuplicateNameException;
import exception.EmptyNameException;
import exception.InvalidPriceException;
import exception.NullNameException;
import model.Decoration;
import org.junit.jupiter.api.Test;
import service.DecorationService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class DecorationServiceTest {

    private DecorationService decorationService;

    @BeforeEach
    void setUp() {
        decorationService = new DecorationService();
    }

    @Test
    void givenValidData_whenCreatingDecoration_thenIsCreated() {

        Decoration decoration = decorationService.createDecoration("Cuaderno de Registro", "cartón y papel", 21.12);

        assertNotNull(decoration);
        assertEquals("Cuaderno de Registro", decoration.getName());
        assertEquals("cartón y papel", decoration.getMaterial());
        assertEquals(21.12, decoration.getPrice());
        assertTrue(decorationService.getDecorations().contains(decoration));    }

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
