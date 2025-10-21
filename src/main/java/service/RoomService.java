package service;

import constant.EntityType;
import exception.factory.ExceptionFactory;
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
            throw ExceptionFactory.duplicateValue(EntityType.ROOM, name);
        }
    }

    public Room createRoom(String name, DifficultyLevel level, double price) {
        checkNotDuplicateName(name);
        Room room = new Room(name.trim(), level, price);
        return roomDAO.save(room);
    }
    private void loadClues(Room room) {
        ClueDAO clueDAO1 = (ClueDAO) clueDAO;
        List<Clue> clues = clueDAO1.findByRoomId(room.getId());
        room.setClues(clues);
    }

    private void loadDecorations(Room room) {
        DecorationDAO decorationDAO1 = (DecorationDAO) decorationDAO;
        List<Decoration> decorations = decorationDAO1.findByRoomId(room.getId());
        room.setDecorations(decorations);
    }

    private void loadRelations(Room room) {
        loadClues(room);
        loadDecorations(room);
    }

    public void addClueToRoom(String roomName, String clueName) {
        Room room = roomDAO.findByName(roomName)
                .orElseThrow(() -> ExceptionFactory.notFound(EntityType.ROOM, roomName));

        Clue clue = clueDAO.findByName(clueName)
                .orElseThrow(() -> ExceptionFactory.notFound(EntityType.CLUE, clueName));

        ClueDAO clueDAO1 = (ClueDAO) clueDAO;
        boolean assigned = clueDAO1.roomAssignment(clue.getId(), room.getId());

        if (assigned) {
            loadClues(room);
        }
    }

    public void addDecorationToRoom(String roomName, String decorationName) {
        Room room = roomDAO.findByName(roomName)
                .orElseThrow(() -> ExceptionFactory.notFound(EntityType.ROOM, roomName));

        Decoration decoration = decorationDAO.findByName(decorationName)
                .orElseThrow(() -> ExceptionFactory.notFound(EntityType.DECORATION, decorationName));

        DecorationDAO decorationDAO1 = (DecorationDAO) decorationDAO;
        boolean assigned = decorationDAO1.roomAssignment(decoration.getId(), room.getId());

        if (assigned) {
            loadDecorations(room);
        }
    }

    public List<Room> getRooms() {
        List<Room> rooms = roomDAO.findAll();
        rooms.forEach(this::loadRelations);
        return rooms;
    }

    public Optional<Room> getRoom(String name) {
        Optional<Room> roomOpt = roomDAO.findByName(name);
        roomOpt.ifPresent(this::loadRelations);
        return roomOpt;
    }

    public Optional<Room> getRoom(Long id) {
        Optional<Room> roomOpt = roomDAO.findById(id);
        roomOpt.ifPresent(this::loadRelations);
        return roomOpt;
    }

    public boolean deleteRoom(Long id) {
        return roomDAO.delete(id);
    }

    public boolean deleteRoom(String name) {
        Optional<Room> room = roomDAO.findByName(name);
        return room.map(r -> roomDAO.delete(r.getId())).orElse(false);
    }

}
