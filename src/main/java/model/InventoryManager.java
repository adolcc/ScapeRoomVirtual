package model;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, RoomInventory> inventories;

    public InventoryManager() {
        this.inventories = new HashMap<>();
    }

    public void createRoomInventory(String roomId) {
        inventories.putIfAbsent(roomId, new RoomInventory(roomId));
    }

    public RoomInventory getRoomInventory(String roomId) {
        return inventories.get(roomId);
    }

    public void addDecorationToRoom(String roomId, Decoration decoration) {
        createRoomInventory(roomId);
        inventories.get(roomId).addDecoration(decoration);
    }

    public boolean removeDecorationFromRoom(String roomId, String decorationName) {
        RoomInventory inventory = inventories.get(roomId);
        return inventory != null && inventory.removeDecoration(decorationName);
    }

    public void addClueToRoom(String roomId, Clue clue) {
        createRoomInventory(roomId);
        inventories.get(roomId).addClue(clue);
    }

    public boolean removeClueFromRoom(String roomId, String clueId) {
        RoomInventory inventory = inventories.get(roomId);
        return inventory != null && inventory.removeClue(clueId);
    }

    public String listRoomInventory(String roomId) {
        RoomInventory inventory = inventories.get(roomId);
        if (inventory == null) {
            return "Sala '" + roomId + "' no encontrada o sin inventario.";
        }
        return inventory.getInventorySummary();
    }

    public String listAllInventory() {
        if (inventories.isEmpty()) {
            return "No hay salas con inventario registrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== INVENTARIO COMPLETO ===\n");
        for (RoomInventory inventory : inventories.values()) {
            sb.append(inventory.getInventorySummary()).append("\n");
        }
        return sb.toString();
    }

    public int getTotalRooms() {
        return inventories.size();
    }

    public boolean roomExists(String roomId) {
        return inventories.containsKey(roomId);
    }
}

