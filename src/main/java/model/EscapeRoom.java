package model;

import exception.EmptyEscapeRoomNameException;
import exception.NullEscapeRoomNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EscapeRoom {

    private Long id;
    private String name;
    private List<Room> rooms;

    public EscapeRoom(String name) {
        validateName(name);

        this.name = name;
        this.rooms = new ArrayList<>();
        this.id = null;
    }

    public String getName() {
        return this.name;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    public Long getId() { return this.id; }

    public void addRoom(Room room) {
        rooms.add(room);
    }
    public void setId(Long id) { this.id = id; }

    private void validateName(String name) {
        if (name == null) {
            throw new NullEscapeRoomNameException();
        }
        if (name.trim().isEmpty()) {
            throw new EmptyEscapeRoomNameException();
        }
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
