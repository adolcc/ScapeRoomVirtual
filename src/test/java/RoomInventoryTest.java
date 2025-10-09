import model.Clue;
import model.Decoration;
import model.RoomInventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class RoomInventoryTest {

    private RoomInventory roomInventory;
    private Decoration decoration;
    private Clue clue;

    @BeforeEach
    public void setUp() {
        roomInventory = new RoomInventory("SalaMisterio");
        decoration = new Decoration("Estatuilla", "Bronce", 25.0);
        clue = new Clue("Pista Secreta", 5.0);
    }

    @Test
    public void testInventoryWithExistingClueAndDecoration() {
        roomInventory.addClue(clue);
        roomInventory.addDecoration(decoration);

        assertEquals(1, roomInventory.getClues().size());
        assertEquals(1, roomInventory.getDecorations().size());
        assertEquals(clue, roomInventory.getClues().get(0));
        assertEquals(decoration, roomInventory.getDecorations().get(0));
    }

    @Test
    public void testRemoveClue() {
        roomInventory.addClue(clue);
        assertTrue(roomInventory.removeClue("Pista Secreta"));
        assertTrue(roomInventory.getClues().isEmpty());
    }

    @Test
    public void testRemoveDecoration() {
        roomInventory.addDecoration(decoration);
        assertTrue(roomInventory.removeDecoration("Estatuilla"));
        assertTrue(roomInventory.getDecorations().isEmpty());
    }

    @Test
    public void testGetInventorySummary() {
        roomInventory.addClue(clue);
        roomInventory.addDecoration(decoration);

        String summary = roomInventory.getInventorySummary();

        assertTrue(summary.contains("SalaMisterio"));
        assertTrue(summary.contains("Estatuilla"));
        assertTrue(summary.contains("Pista Secreta"));
        assertTrue(summary.contains("25.0"));
        assertTrue(summary.contains("5.0"));
    }

    @Test
    public void testEmptyInventory() {
        assertTrue(roomInventory.isEmpty());
        assertEquals(0, roomInventory.getTotalItems());

        String summary = roomInventory.getInventorySummary();
        assertTrue(summary.contains("No hay decoraciones"));
        assertTrue(summary.contains("No hay pistas"));
    }

    @Test
    public void testPriceCalculations() {
        roomInventory.addDecoration(decoration);
        roomInventory.addClue(clue);

        assertEquals(25.0, roomInventory.getTotalDecorationPrice());
        assertEquals(5.0, roomInventory.getTotalCluePrice());
        assertEquals(30.0, roomInventory.getTotalRoomPrice());
    }

    @Test
    public void testPriceBreakdown() {
        roomInventory.addDecoration(decoration);
        roomInventory.addClue(clue);

        String breakdown = roomInventory.getPriceBreakdown();
        assertTrue(breakdown.contains("SalaMisterio- 5.00"));
        assertTrue(breakdown.contains("5.00"));
        assertTrue(breakdown.contains("30.00"));
    }
}
