package models;

import exceptions.EmptyRoomClueException;
import exceptions.NullRoomClueException;

public class Room {

    private String clue;

    public Room(String clue) {
        if (clue == null) {
            throw new NullRoomClueException("La pista no puede ser nula");
        }
        if (clue.trim().isEmpty()) {
            throw new EmptyRoomClueException("La sala debe tener al menos una pista");
        }
        this.clue = clue;
    }

    public String getClue() {
        return clue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return clue.equals(room.clue);
    }

    @Override
    public int hashCode() {
        return clue.hashCode();
    }

}
