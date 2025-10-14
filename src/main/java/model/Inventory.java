package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        System.out.println("Sala añadida: " + room.getName());
    }

    public void addClue(Clue clue) {
        clues.add(clue);
        updateTotalValue();
        System.out.println("Pista añadida: " + clue);
    }

    public void addDecorationItem(Decoration item) {
        decorations.add(item);
        updateTotalValue();
        System.out.println("Objeto de decoración añadido: " + item.getMaterial());
    }

    public void removeRoom(String roomName) {
        rooms.removeIf(room -> room.getName().equalsIgnoreCase(roomName));
        updateTotalValue();
        System.out.println("Sala eliminada: " + roomName);
    }

    public void removeClue(String clueTheme) {
        clues.removeIf(clue -> clue.getClue().equalsIgnoreCase(clueTheme));
        updateTotalValue();
        System.out.println("Pista eliminada: " + clueTheme);
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

    public void removeDecorationItem(String material) {
        decorations.removeIf(item -> item.getMaterial().equalsIgnoreCase(material));
        updateTotalValue();
        System.out.println("Objeto de decoración eliminado: " + material);
    }

    public void showInventory() {
        System.out.println("==INVENTARIO==");
        System.out.println(" Total de salas:" + rooms.size());
        System.out.println(" total de pistas:" + clues.size());
        System.out.println(" total de decoraciones:" + decorations.size());
        System.out.println(" Valor total inventario:" + totalInventoryValue);

    }
}

