package service;


import exception.DuplicateRoomNameException;
import exception.EmptyRoomNameException;
import exception.NullEscapeRoomNameException;
import model.Room;

import java.util.HashSet;
import java.util.Set;

public class RoomService {

    private Set<Room> roomSet;

    public RoomService() {
        this.roomSet = new HashSet<>();
    }

    public void checkNotNullName(String name) {
        if (name == null) {
            throw new NullEscapeRoomNameException();
        }
    }

    public void checkNotEmptyName(String name) {
        name = name.trim();
        if (name.isEmpty()) {
            throw new EmptyRoomNameException();
        }
    }

    public void checkNotDuplicateName(String name) {
        if (roomSet.contains(new Room(name, 1))) { // nivel ficticio para comparación
            throw new DuplicateRoomNameException();
        }
    }

    public void createRoom(String name, int level) {
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        roomSet.add(new Room(name.trim(), level));
    }

    public Set<Room> getRooms() {
        return roomSet;
    }
}
