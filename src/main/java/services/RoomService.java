package services;

import exceptions.DuplicateRoomClueException;
import exceptions.EmptyRoomClueException;
import exceptions.NullRoomClueException;
import models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private List<String> clues = new ArrayList<>();


    public void addClue(String clue) {

        if (clue == null) {
            throw new NullRoomClueException("La pista no puede ser nula");
        }

        if (clue.trim().isEmpty()) {
            throw new EmptyRoomClueException("La sala debe tener al menos una pista");
        }

        if (clues.contains(clue)) {
            throw new DuplicateRoomClueException("La pista ya existe");
        }

        clues.add(clue);
    }

    public List<String> getClues() {
        return new ArrayList<>(clues);
    }

}

