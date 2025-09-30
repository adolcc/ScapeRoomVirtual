package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EscapeRoom {

    private String name;
    private List<Room> rooms;
    public EscapeRoom(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }


    public List<Room> getRooms() {
        return rooms;
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        EscapeRoom other = (EscapeRoom) o;
        return this.name.equalsIgnoreCase(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name.toLowerCase());
    }

}
