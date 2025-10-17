package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Inventory {
    private List<Room> rooms;
    private List<Clue> clues;
    private List<Decoration> decorations;
    private double totalInventoryValue;

    public Inventory() {
        this.rooms = new ArrayList<>();
        this.clues = new ArrayList<>();
        this.decorations = new ArrayList<>();
        this.totalInventoryValue = 0.0;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public List<Decoration> getDecorationItems() {
        return decorations;
    }

    public double getTotalInventoryValue() {
        return totalInventoryValue;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        updateTotalValue();
    }

    public void addClue(Clue clue) {
        clues.add(clue);
        updateTotalValue();
    }

    public void addDecorationItem(Decoration item) {
        decorations.add(item);
        updateTotalValue();
    }

    public boolean removeRoom(String roomName) {
        boolean removed = rooms.removeIf(room -> room.getName().equalsIgnoreCase(roomName));
        if (removed) {
            updateTotalValue();
        }
        return removed;
    }

    public boolean removeClue(String clueName) {
        boolean removed = clues.removeIf(clue -> clue.getName().equalsIgnoreCase(clueName));
        if (removed) {
            updateTotalValue();
        }
        return removed;
    }

    private void updateTotalValue() {
        totalInventoryValue = 0.0;

        for (Room room : rooms) {
            totalInventoryValue += room.getPrice();
        }
        for (Clue clue : clues) {
            totalInventoryValue += clue.getPrice();
        }
        for (Decoration item : decorations) {
            totalInventoryValue += item.getPrice();
        }

    }

    public boolean removeDecorationItem(String name) {
        boolean removed = decorations.removeIf(item -> item.getName().equalsIgnoreCase(name));
        if (removed) {
            updateTotalValue();
        }
        return removed;
    }

    public InventoryStats getInventoryStats() {
        return new InventoryStats(
                rooms.size(),
                clues.size(),
                decorations.size(),
                totalInventoryValue);

    }

    public double getTotalAssetsByRoom(String roomName) {
        Optional<Room> targetRoomOpt = rooms.stream()
                .filter(room -> room.getName().equalsIgnoreCase(roomName))
                .findFirst();

        if (targetRoomOpt.isEmpty()) {
            throw new IllegalArgumentException("Sala no encontrada: " + roomName);
        }

        Room targetRoom = targetRoomOpt.get();
        double total = targetRoom.getPrice();

        total += clues.stream()
                .filter(clue -> targetRoom.getId().equals(clue.getRoomId()))
                .mapToDouble(Clue::getPrice)
                .sum();

        total += decorations.stream()
                .filter(decoration -> targetRoom.getId().equals(decoration.getRoomId()))
                .mapToDouble(Decoration::getPrice)
                .sum();

        return total;
    }

    public double getTotalAssetsByEscapeRoom(List<Room> escapeRoomRooms) {
        double total = 0.0;

        for (Room room : escapeRoomRooms) {
            total += getTotalAssetsByRoom(room.getName());
        }

        return total;
    }

    public Optional<Room> findRoomByName(String roomName) {
        return rooms.stream()
                .filter(room -> room.getName().equalsIgnoreCase(roomName))
                .findFirst();
    }

    public Optional<Clue> findClueByName(String clueName) {
        return clues.stream()
                .filter(clue -> clue.getName().equalsIgnoreCase(clueName))
                .findFirst();
    }

    public Optional<Decoration> findDecorationByName(String decorationName) {
        return decorations.stream()
                .filter(decoration -> decoration.getName().equalsIgnoreCase(decorationName))
                .findFirst();
    }
}

