package service;

import model.Clue;
import model.Inventory;
import model.Room;


public class InventoryService {

    private Inventory inventory;



    public InventoryService() {
        this.inventory = new Inventory();


    }

    public void createNewRoom(String name, int difficultyLevel, double price) {
        Room room = new Room(name, difficultyLevel);
        inventory.addRoom(room);

    }   public void createNewClue(String theme, double price) {
        Clue clue = new Clue(theme, price);
        inventory.addClue(clue);

    }


    private String generateInventorySummary() {
        return String.format("Inventario - Salas: %d, Pistas: %d, Objetos: %d, Valor Total: %.2f€",
                inventory.getRooms().size(),
                inventory.getClues().size(),
                inventory.getDecorationItems().size(),
                inventory.getTotalInventoryValue());
    }
    public void removeRoomFromInventory(String roomName) {
        inventory.removeRoom(roomName);

    }

    public void removeClueFromInventory(String clueTheme) {
        inventory.removeClue(clueTheme);

    }

    public void removeDecorationItemFromInventory(String material) {
        inventory.removeDecorationItem(material);

    }

    public void displayFullInventory() {
        inventory.showInventory();
    }

    public double getTotalInventoryValue() {
        return inventory.getTotalInventoryValue();
    }
}
