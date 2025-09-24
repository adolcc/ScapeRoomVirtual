package services;

import exceptions.DuplicateEscapeRoomNameException;
import exceptions.EmptyEscapeRoomNameException;
import exceptions.NullEscapeRoomNameException;
import models.EscapeRoom;

import java.util.HashSet;
import java.util.Set;

public class EscapeRoomService {

    private Set<EscapeRoom> escapeRoomSet;

    public EscapeRoomService(){
        this.escapeRoomSet = new HashSet<>();
    }

    public void checkNotNullName(String name) {
        if(name == null) {
            throw new NullEscapeRoomNameException();
        }
    }

    public void checkNotEmptyName(String name) {
        if(name.trim().isEmpty()) {
            throw new EmptyEscapeRoomNameException();
        }
    }

    public void checkNotDuplicateName(String name) {
        if(escapeRoomSet.contains(new EscapeRoom(name))) {
            throw new DuplicateEscapeRoomNameException();
        }
    }

    public void createEscapeRoom(String name) {
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        escapeRoomSet.add(new EscapeRoom(name));
    }

    public Set<EscapeRoom> getEscapeRooms() {
        return escapeRoomSet;
    }
}
