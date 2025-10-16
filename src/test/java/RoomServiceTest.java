import exception.*;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.database.DatabaseSetup;
import service.RoomService;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class RoomServiceTest {

    private RoomService roomService;
    private DatabaseSetup dbSetup;

    @BeforeEach
    void setUp() throws SQLException {
        dbSetup = new DatabaseSetup();
        dbSetup.cleanDatabase();
        roomService = new RoomService();

    }
    @AfterEach
    void tearDown() throws SQLException {
        dbSetup.cleanDatabase();
    }

    @Test
    void givenValidRoom_whenCreatingRoom_thenSucces() {
        String name = "Room Egipcio";
        int level = 3;
        double price = 100.0;

        Room room = roomService.createRoom(name, level, price);

        assertNotNull(room);
        assertEquals(name, room.getName());
        assertEquals(level, room.getLevel());
        assertEquals(price, room.getPrice());

    }

    @Test
    void givenNullName_whenCreatingRoom_thenThrowException() {
        String name = null;
        int level = 3;
        double price = 100.0;

        Exception e = assertThrows(NullEscapeRoomNameException.class, () -> {
            roomService.createRoom(name, level, price);
        });
        assertEquals("El nombre de la sala no puede ser nulo.", e.getMessage());
    }
    @Test
    void givenEmptyRoomName_whenCreating_thenThrowException() {
        String name = "   ";
        int level = 3;
        double price = 100.0;

        Exception e =assertThrows(EmptyRoomNameException.class, () -> {
            roomService.createRoom(name, level, price);
        });
        assertEquals("El nombre de la sala no puede estar vacío.", e.getMessage());
    }
    @Test
    void givenDuplicateRoomName_whenCreating_thenThrowException() {
        String name = "Room Egipcio";
        int level1 = 3;
        int level2 = 5;
        double price = 100.0;

        roomService.createRoom(name, level1, price);
        Exception e = assertThrows(DuplicateRoomNameException.class, () -> {
            roomService.createRoom(name, level2, price);
        });
        assertEquals("Ya existe una sala con ese nombre.", e.getMessage());
    }
    @Test
    void givenRoomNameWithSpaces_whenCreating_thenNameIsTrimmed() {
        String nameWithSpaces = "  Room Egipcio  ";
        String expectedName = "Room Egipcio";
        int level = 3;
        double price = 100.0;

        Room room = roomService.createRoom(nameWithSpaces, level, price);
        assertEquals(expectedName, room.getName());
    }
    @Test
    void givenMultipleRooms_whenGettingRooms_thenAllRoomsAreReturned() {
        roomService.createRoom("Room 1", 1, 50.0);
        roomService.createRoom("Room 2", 2, 75.0);
        roomService.createRoom("Room 3", 3, 100.0);

        List<Room> rooms = roomService.getRooms();
        assertFalse(rooms.isEmpty());
        assertEquals(3, rooms.size());
    }
    @Test
    void givenExistingRoomName_whenGettingRoomByName_thenRoomIsReturned() {
        String name = "Room Egipcio";
        int level = 3;
        double price = 100.0;
        roomService.createRoom(name, level, price);

        Optional<Room> foundRoom = roomService.getRoom(name);
        assertTrue(foundRoom.isPresent());
        assertEquals(name, foundRoom.get().getName());
        assertEquals(level, foundRoom.get().getLevel());
        assertEquals(price, foundRoom.get().getPrice());
    }
    @Test
    void givenNonExistingRoomName_whenGettingRoomByName_thenEmptyIsReturned() {
        String name = "Non Existing Room";

        Optional<Room> foundRoom = roomService.getRoom(name);
        assertFalse(foundRoom.isPresent());
    }@Test
    void givenExistingRoomId_whenGettingRoomById_thenRoomIsReturned() {
        Room createdRoom = roomService.createRoom("Room Egipcio", 3, 100.0);
        Long roomId = createdRoom.getId();

        Optional<Room> foundRoom = roomService.getRoom(roomId);
        assertTrue(foundRoom.isPresent());
        assertEquals(roomId, foundRoom.get().getId());
        assertEquals("Room Egipcio", foundRoom.get().getName());
    }
    @Test
    void givenNonExistingRoomId_whenGettingRoomById_thenEmptyIsReturned() {
        Long nonExistingId = 999L;

        Optional<Room> foundRoom = roomService.getRoom(nonExistingId);
        assertFalse(foundRoom.isPresent());
    }
    @Test
    void givenExistingRoomId_whenDeletingRoomById_thenRoomIsDeleted() {
        Room createdRoom = roomService.createRoom("Room Egipcio", 3, 100.0);
        Long roomId = createdRoom.getId();

        boolean isDeleted = roomService.deleteRoom(roomId);
        assertTrue(isDeleted);
        Optional<Room> foundRoom = roomService.getRoom(roomId);
        assertFalse(foundRoom.isPresent());
    }
    @Test
    void givenNonExistingRoomId_whenDeletingRoomById_thenFalse() {
        Long nonExistingId = 999L;

        boolean isDeleted = roomService.deleteRoom(nonExistingId);
        assertFalse(isDeleted);
    }
    @Test
    void givenExistingRoomName_whenDeletingRoomByName_thenRoomIsDeleted() {
        roomService.createRoom("Room Egipcio", 3, 100.0);

        boolean isDeleted = roomService.deleteRoom("Room Egipcio");
        assertTrue(isDeleted);
        Optional<Room> foundRoom = roomService.getRoom("Room Egipcio");
        assertFalse(foundRoom.isPresent());
    }
    @Test
    void givenNonExistingRoomName_whenDeletingRoomByName_thenFalse() {
        String nonExistingName = "Non Existing Room";

        boolean isDeleted = roomService.deleteRoom(nonExistingName);
        assertFalse(isDeleted);
    }
    @Test
    void givenInvalidPrice_whenCreatingRoom_thenThrowException() {
        String name = "Room Test";
        int level = 3;
        double invalidPrice = 0.0;

       Exception e = assertThrows(InvalidPriceException.class, () -> {
            roomService.createRoom(name, level, invalidPrice);
        });
        assertEquals("El precio debe ser mayor a 0.", e.getMessage());
    }
    @Test
    void givenNegativePrice_whenCreatingRoom_thenThrowInvalidPriceException() {

        assertThrows(InvalidPriceException.class, () -> {
            roomService.createRoom("Test Room", 1, -10.0);
        });
    }
}