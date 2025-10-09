package model;

import java.util.ArrayList;
import java.util.List;

public class RoomInventory {
    private String roomId;
    private List<Decoration> decorations;
    private List<Clue> clues;

    public RoomInventory(String roomId) {
        this.roomId = roomId;
        this.clues = new ArrayList<>();
        this.decorations = new ArrayList<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public List<Clue> getClues() {
        return new ArrayList<>(clues);
    }

    public List<Decoration> getDecorations() {
        return new ArrayList<>(decorations);
    }

    public void addDecoration(Decoration decoration) {
        if (decoration != null) {
            decorations.add(decoration);
        }
    }

    public boolean removeDecoration(String decorationName) {
        return decorations.removeIf(decoration -> decoration.getName().equalsIgnoreCase(decorationName));
    }

    public void addClue(Clue clue) {
        if (clue != null) {
            clues.add(clue);
        }
    }

    public boolean removeClue(String clueId) {
        return clues.removeIf(clue ->
                clue.getId().equalsIgnoreCase(clueId));

    }

    public boolean isEmpty() {
        return decorations.isEmpty() && clues.isEmpty();
    }

    public int getTotalItems() {
        return decorations.size() + clues.size();
    }

    public String getInventorySummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Inventario Sala: ").append(roomId).append(" ===\n");

        sb.append("Decoraciones (").append(decorations.size()).append("):\n");
        for (Decoration decor : decorations) {
            sb.append("  - ").append(decor.getName())
                    .append(" (").append(decor.getMaterial()).append(") - $")
                    .append(decor.getPrice()).append("\n");
        }

        sb.append("Pistas (").append(clues.size()).append("):\n");
        for (Clue clue : clues) {
            sb.append("  - [").append(clue.getId()).append("] ")
                    .append(clue.getContent()).append("\n");
        }

        return sb.toString();
    }

    public double getTotalDecorationPrice() {
        return decorations.stream()
                .mapToDouble(Decoration::getPrice)
                .sum();
    }

    public double getTotalCluePrice() {
        return clues.stream()
                .mapToDouble(Clue::getPrice)
                .sum();
    }

    public double getTotalRoomPrice() {
        return getTotalDecorationPrice() + getTotalCluePrice();
    }

    public String getPriceBreakdown() {
        return String.format(
                "Sala %s - Decoraciones: $%.2f, Pistas: $%.2f, TOTAL: $%.2f",
                roomId, getTotalDecorationPrice(), getTotalCluePrice(), getTotalRoomPrice()
        );
    }
}


