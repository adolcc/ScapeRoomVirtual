package service;

import model.*;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {

    private Inventory inventory;

    public InventoryService() {
        this.inventory = new Inventory();

    }

    public Room createNewRoom(String name, int difficultyLevel, double price) {
        Room room = new Room(name, difficultyLevel,price);
        inventory.addRoom(room);
        return room;

    }
    public Clue createNewClue(String theme, double price) {
        Clue clue = new Clue(theme, price);
        inventory.addClue(clue);
        return clue;

    }
    public Decoration createNewDecoration(String name, String material, double price) {
        Decoration decoration = new Decoration(name, material, price);
        inventory.addDecorationItem(decoration);
        return decoration;
    }


    public String generateInventorySummary() {
        return String.format("Inventario - Salas: %d, Pistas: %d, Objetos: %d, Valor Total: %.2f€",
                inventory.getRooms().size(),
                inventory.getClues().size(),
                inventory.getDecorationItems().size(),
                inventory.getTotalInventoryValue());
    }
    public boolean removeRoomFromInventory(String roomName) {
         return inventory.removeRoom(roomName);

    }

    public  boolean removeClueFromInventory(String clueName) {
         return inventory.removeClue(clueName);

    }

    public boolean removeDecorationItemFromInventory(String decorationName) {
        return inventory.removeDecorationItem(decorationName);

    }
    public double getTotalAssetsByRoom(String roomName) {
        return inventory.getTotalAssetsByRoom(roomName);
    }
    public double getTotalAssetsByEscapeRoom(List<Room> escapeRoomRooms) {
        return inventory.getTotalAssetsByEscapeRoom(escapeRoomRooms);
    }
    public double getTotalInventoryValue() {
        return inventory.getTotalInventoryValue();
    }
    public List<Room> getAllRooms() {
        return inventory.getRooms();
    }

    public List<Clue> getAllClues() {
        return inventory.getClues();
    }
    public List<Decoration> getAllDecorations() {
        return inventory.getDecorationItems();
    }
    public Room findRoomByName(String roomName) {
        return inventory.findRoomByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada: " + roomName));
    } public Clue findClueByName(String clueName) {
        return inventory.findClueByName(clueName)
                .orElseThrow(() -> new IllegalArgumentException("Pista no encontrada: " + clueName));
    }

    public Decoration findDecorationByName(String decorationName) {
        return inventory.findDecorationByName(decorationName)
                .orElseThrow(() -> new IllegalArgumentException("Decoración no encontrada: " + decorationName));
    }

    public InventoryStats getInventoryStats() {
        return inventory.getInventoryStats();
    }

    public List<RoomAssets> getRoomAssetsDetails() {
        return inventory.getRooms().stream()
                .map(room -> new RoomAssets(
                        room.getName(),
                        inventory.getTotalAssetsByRoom(room.getName())
                ))
                .collect(Collectors.toList());
    }

}
