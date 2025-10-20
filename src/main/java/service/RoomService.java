package service;

import exception.*;
import model.Clue;
import model.Decoration;
import model.DifficultyLevel;
import model.Room;
import repository.dao.ClueDAO;
import repository.dao.DecorationDAO;
import repository.dao.GenericDAO;
import repository.dao.RoomDAO;

import java.util.List;
import java.util.Optional;

public class RoomService {

    private final GenericDAO<Room, Long> roomDAO;
    private final GenericDAO<Clue, Long> clueDAO;
    private final GenericDAO<Decoration, Long> decorationDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
        this.clueDAO = new ClueDAO();
        this.decorationDAO = new DecorationDAO();

    }

    private void checkNotDuplicateName(String name) {
        if (roomDAO.findByName(name).isPresent()) {
            throw new DuplicateRoomNameException();
        }
    }

    public Room createRoom(String name, DifficultyLevel level, double price) {
        checkNotDuplicateName(name);
        Room room = new Room(name.trim(), level, price);
        return roomDAO.save(room);
    }

    public void addDecorationToRoom(String roomName, String decorationName) {
        Room room = roomDAO.findByName(roomName)
                .orElseThrow(() -> new RoomNotFoundException("Sala inexistente"));

        Decoration decoration = decorationDAO.findByName(decorationName)
                .orElseThrow(() -> new DecorationNotFoundException("Decoración no encontrada"));

        DecorationDAO decorationDAO1 = (DecorationDAO) decorationDAO;

        decorationDAO1.roomAssignment(decoration.getId(), room.getId());
    }

    public void addClueToRoom(String roomName, String clueName) {
        Room room = roomDAO.findByName(roomName)
                .orElseThrow(() -> new RoomNotFoundException("Sala inexistente: " + roomName));

        Clue clue = clueDAO.findByName(clueName)
                .orElseThrow(() -> new ClueNotFoundException("Pista no encontrada: " + clueName));

        ClueDAO clueDAO1 = (ClueDAO) clueDAO;
        clueDAO1.roomAssignment(clue.getId(), room.getId());
    }

    public List<Room> getRooms() {
        return roomDAO.findAll();
    }

    public Optional<Room> getRoom(String name) {
        return roomDAO.findByName(name);
    }

    public Optional<Room> getRoom(Long id) {
        return roomDAO.findById(id);
    }

    public boolean deleteRoom(Long id) {
        return roomDAO.delete(id);
    }

    public boolean deleteRoom(String name) {
        Optional<Room> room = roomDAO.findByName(name);
        return room.map(r -> roomDAO.delete(r.getId())).orElse(false);
    }

}
