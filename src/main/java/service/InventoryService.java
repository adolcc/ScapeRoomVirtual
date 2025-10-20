package service;

import model.*;
import repository.dao.ClueDAO;
import repository.dao.DecorationDAO;
import repository.dao.RoomDAO;

import java.util.List;


public class InventoryService {

    private final RoomDAO roomDAO;
    private final ClueDAO clueDAO;
    private final DecorationDAO decorationDAO;

    public InventoryService(RoomDAO roomDAO, ClueDAO clueDAO, DecorationDAO decorationDAO) {
        this.roomDAO = roomDAO;
        this.clueDAO = clueDAO;
        this.decorationDAO = decorationDAO;

    }

    public List<Room> loadAllRooms() {
        return this.roomDAO.findAll();
    }

    public List<Clue> loadAllClues() {
        return this.clueDAO.findAll();
    }

    public List<Decoration> loadAllDecorations() {
        return this.decorationDAO.findAll();
    }

    public InventoryStats getInventoryStats() {

        List<Room> rooms = loadAllRooms();
        List<Clue> clues = loadAllClues();
        List<Decoration> decorations = loadAllDecorations();

        return Inventory.generateStats(rooms, clues, decorations);
    }

    public String generateInventorySummary() {
        List<Room> rooms = loadAllRooms();
        List<Clue> clues = loadAllClues();
        List<Decoration> decorations = loadAllDecorations();

        return Inventory.generateSummary(rooms, clues, decorations);
    }

    public double calculateTotalAssetsByRoom(String roomName) {
        Room room = this.roomDAO.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada: " + roomName));

        List<Clue> allClues = loadAllClues();
        List<Decoration> allDecorations = loadAllDecorations();

        return Inventory.calculateAssetsByRoom(room, allClues, allDecorations);
    }

    public double calculateTotalAssetsByEscapeRoom(Long escapeRoomId) {
        List<Room> escapeRoomRooms = this.roomDAO.findByEscapeRoomId(escapeRoomId);

        if (escapeRoomRooms.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron salas para el Escape Room ID: " + escapeRoomId);
        }

        List<Clue> allClues = loadAllClues();
        List<Decoration> allDecorations = loadAllDecorations();

        return Inventory.calculateAssetsByRoomList(escapeRoomRooms, allClues, allDecorations);
    }

    public List<RoomAssets> getRoomAssetsDetails() {
        List<Room> rooms = loadAllRooms();
        List<Clue> clues = loadAllClues();
        List<Decoration> decorations = loadAllDecorations();

        return Inventory.getRoomAssetsDetails(rooms, clues, decorations);
    }

    public Room findRoomByName(String roomName) {
        return this.roomDAO.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada: " + roomName));
    }

    public Clue findClueByName(String clueName) {
        return this.clueDAO.findByName(clueName)
                .orElseThrow(() -> new IllegalArgumentException("Pista no encontrada: " + clueName));
    }

    public Decoration findDecorationByName(String decorationName) {
        return this.decorationDAO.findByName(decorationName)
                .orElseThrow(() -> new IllegalArgumentException("Decoración no encontrada: " + decorationName));
    }

    public double getAverageRoomPrice() {
        List<Room> rooms = loadAllRooms();
        if (rooms.isEmpty()) return 0.0;

        return rooms.stream()
                .mapToDouble(Room::getPrice)
                .average()
                .orElse(0.0);
    }

    public Room getMostExpensiveRoom() {
        List<Room> rooms = loadAllRooms();
        return rooms.stream()
                .max((r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice()))
                .orElseThrow(() -> new IllegalStateException("No hay salas disponibles"));
    }
    public Room getCheapestRoom() {
        List<Room> rooms = loadAllRooms();
        return Inventory.findCheapestRoom(rooms)
                .orElseThrow(() -> new IllegalStateException("No hay salas disponibles"));
    }

    public String getInventoryHealthStatus() {
        InventoryStats stats = getInventoryStats();

        if (stats.getTotalValue() == 0) {
            return "CRÍTICO - Inventario vacío";
        } else if (stats.getRoomCount() == 0) {
            return "ALTO RIESGO - No hay salas";
        } else if (stats.getTotalValue() < 1000) {
            return "BAJO - Valor total insuficiente";
        } else {
            return "SALUDABLE - Inventario en buen estado";
        }
    }
}


