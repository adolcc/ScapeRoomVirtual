package model;

public class RoomAssets {
    private final String roomName;
    private final double totalAssets;

    public RoomAssets(String roomName, double totalAssets) {
        this.roomName = roomName;
        this.totalAssets = totalAssets;
    }

    public String getRoomName() { return roomName; }
    public double getTotalAssets() { return totalAssets; }

}
