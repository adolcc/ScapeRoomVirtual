package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EscapeRoom {

    private Long id;
    private String name;
    private List<Room> rooms;

    public EscapeRoom(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.id = null;
    }

    public String getName() {
        return this.name;
    }
    public Long getId() { return this.id; }
    public List<Room> getRooms() {
        return rooms;
    }

    public void setName(String name) { this.name = name; }
    public void setId(Long id) { this.id = id; }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscapeRoom other = (EscapeRoom) o;

        if (this.name == null && other.getName() == null) return true;
        if (this.name == null || other.getName() == null) return false;

        return this.name.equalsIgnoreCase(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name != null ? this.name.toLowerCase() : null);
    }

}
