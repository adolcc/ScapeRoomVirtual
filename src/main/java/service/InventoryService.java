package service;

import model.*;
import repository.dao.ClueDAO;
import repository.dao.DecorationDAO;
import repository.dao.RoomDAO;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

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

        return generateStats(rooms, clues, decorations);
    }

    public String generateInventorySummary() {
        List<Room> rooms = loadAllRooms();
        List<Clue> clues = loadAllClues();
        List<Decoration> decorations = loadAllDecorations();

        return generateSummary(rooms, clues, decorations);
    }

    public List<RoomAssets> getRoomAssetsDetails() {

        List<Room> rooms =loadAllRooms();
        List<Clue> clues = loadAllClues();
        List<Decoration> decorations = loadAllDecorations();

        return getRoomAssetsDetails(rooms, clues, decorations);
    }
    public double getAverageRoomPrice() {
        List<Room> rooms = loadAllRooms();
        return calculateAverageRoomPrice(rooms);
    }

    public Room getMostExpensiveRoom() {
        List<Room> rooms = loadAllRooms();
        return findMostExpensiveRoom(rooms)
                .orElseThrow(() -> new IllegalStateException("No hay salas disponibles"));
    }

    public Room getCheapestRoom() {
        List<Room> rooms = loadAllRooms();
        return findCheapestRoom(rooms)
                .orElseThrow(() -> new IllegalStateException("No hay salas disponibles"));
    }

    public String getInventoryHealthStatus() {
        List<Room> rooms = loadAllRooms();
        List<Clue> clues = loadAllClues();
        List<Decoration> decorations = loadAllDecorations();

        return evaluateInventoryHealth(rooms, clues, decorations);
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

    public double calculateTotalAssetsByRoom(String roomName) {
        Room room = this.roomDAO.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada: " + roomName));

        List<Clue> allClues = loadAllClues();
        List<Decoration> allDecorations = loadAllDecorations();

        return calculateAssetsByRoom(room, allClues, allDecorations);
    }

    public double calculateTotalAssetsByEscapeRoom(Long escapeRoomId) {
        List<Room> escapeRoomRooms = this.roomDAO.findByEscapeRoomId(escapeRoomId);

        if (escapeRoomRooms.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron salas para el Escape Room ID: " + escapeRoomId);
        }

        List<Clue> allClues = loadAllClues();
        List<Decoration> allDecorations = loadAllDecorations();

        return calculateAssetsByRoomList(escapeRoomRooms, allClues, allDecorations);
    }

    private double calculateTotalValue(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        double total = 0.0;

        if (rooms != null) {
            total += rooms.stream().mapToDouble(Room::getPrice).sum();
        }
        if (clues != null) {
            total += clues.stream().mapToDouble(Clue::getPrice).sum();
        }
        if (decorations != null) {
            total += decorations.stream().mapToDouble(Decoration::getPrice).sum();
        }

        return total;
    }

    private InventoryStats generateStats(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        int roomCount = rooms != null ? rooms.size() : 0;
        int clueCount = clues != null ? clues.size() : 0;
        int decorationCount = decorations != null ? decorations.size() : 0;
        double totalValue = calculateTotalValue(rooms, clues, decorations);

        return new InventoryStats(roomCount, clueCount, decorationCount, totalValue);
    }

    private double calculateAssetsByRoom(Room room, List<Clue> allClues, List<Decoration> allDecorations) {
        if (room == null) {
            return 0.0;
        }

        double total = room.getPrice();

        if (allClues != null) {
            total += allClues.stream()
                    .filter(clue -> room.getId() != null && room.getId().equals(clue.getRoomId()))
                    .mapToDouble(Clue::getPrice)
                    .sum();
        }

        if (allDecorations != null) {
            total += allDecorations.stream()
                    .filter(decoration -> room.getId() != null && room.getId().equals(decoration.getRoomId()))
                    .mapToDouble(Decoration::getPrice)
                    .sum();
        }

        return total;
    }

    private double calculateAssetsByRoomList(List<Room> rooms, List<Clue> allClues, List<Decoration> allDecorations) {
        if (rooms == null || rooms.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Room room : rooms) {
            total += calculateAssetsByRoom(room, allClues, allDecorations);
        }
        return total;
    }

    private String generateSummary(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        InventoryStats stats = generateStats(rooms, clues, decorations);
        return String.format("Inventario - Salas: %d, Pistas: %d, Decoraciones: %d, Valor Total: %.2f€",
                stats.getRoomCount(),
                stats.getClueCount(),
                stats.getDecorationCount(),
                stats.getTotalValue());
    }

    private List<RoomAssets> getRoomAssetsDetails(List<Room> rooms, List<Clue> allClues, List<Decoration> allDecorations) {
        if (rooms == null) {
            return List.of();
        }

        return rooms.stream()
                .map(room -> new RoomAssets(
                        room.getName(),
                        calculateAssetsByRoom(room, allClues, allDecorations)
                ))
                .collect(Collectors.toList());
    }

    private double calculateAverageRoomPrice(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return 0.0;
        }
        return rooms.stream()
                .mapToDouble(Room::getPrice)
                .average()
                .orElse(0.0);
    }

    private Optional<Room> findMostExpensiveRoom(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return Optional.empty();
        }
        return rooms.stream()
                .max((r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice()));
    }

    private Optional<Room> findCheapestRoom(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return Optional.empty();
        }
        return rooms.stream()
                .min((r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice()));
    }

    private String evaluateInventoryHealth(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        InventoryStats stats = generateStats(rooms, clues, decorations);

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




