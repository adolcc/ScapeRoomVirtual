package model;

public class InventoryStats {
    private final int roomCount;
    private final int clueCount;
    private final int decorationCount;
    private final double totalValue;

    public InventoryStats(int roomCount, int clueCount, int decorationCount, double totalValue) {
        this.roomCount = roomCount;
        this.clueCount = clueCount;
        this.decorationCount = decorationCount;
        this.totalValue = totalValue;
    }


    public int getRoomCount() { return roomCount; }
    public int getClueCount() { return clueCount; }
    public int getDecorationCount() { return decorationCount; }
    public double getTotalValue() { return totalValue; }

    public boolean isEmpty() {
        return roomCount == 0 && clueCount == 0 && decorationCount == 0;
    }

}


