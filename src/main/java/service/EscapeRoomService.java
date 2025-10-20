package service;

import exception.*;
import model.EscapeRoom;
import model.Room;
import repository.dao.EscapeRoomDAO;
import repository.dao.GenericDAO;
import repository.dao.RoomDAO;

import java.util.List;
import java.util.Optional;

public class EscapeRoomService {

    private final GenericDAO<EscapeRoom, Long> escapeRoomDAO;
    private final GenericDAO<Room, Long> roomDAO;
    private final RoomService roomService;

    public EscapeRoomService() {
        this.escapeRoomDAO = new EscapeRoomDAO();
        this.roomDAO = new RoomDAO();
        this.roomService = new RoomService();
    }

    private void checkNotDuplicateName(String name) {
        if (escapeRoomDAO.findByName(name).isPresent()) {
            throw new DuplicateEscapeRoomNameException();
        }
    }

    public EscapeRoom createEscapeRoom(String name) {
        checkNotDuplicateName(name);
        EscapeRoom escapeRoom = new EscapeRoom(name);
        return escapeRoomDAO.save(escapeRoom);
    }

    public void addRoomToEscapeRoom(String escapeRoomName, Room room) {
        EscapeRoom escapeRoom = escapeRoomDAO.findByName(escapeRoomName)
                .orElseThrow(EscapeRoomNotFoundException::new);
        Room roomToAssign = roomService.getRoom(room.getId()).orElseThrow(() -> new RoomNotFoundException("Sala no encontrada."));

        if (escapeRoom.getRooms().contains(room)) {
            throw new DuplicateRoomNameException();
        }
        if (roomToAssign.getClues().size() < 2) {
            throw new InsufficientCluesException();
        }
        if (roomToAssign.getDecorations().size() < 2) {
            throw new InsufficientDecorationsException();
        }

        RoomDAO roomDAO1 = (RoomDAO) roomDAO;
        boolean assigned = roomDAO1.escapeRoomAssignment(room.getId(), escapeRoom.getId());

        if (assigned) {
            escapeRoom.addRoom(room);
        }
    }

    public List<EscapeRoom> getEscapeRooms() {
        return escapeRoomDAO.findAll();
    }

    public Optional<EscapeRoom> getEscapeRoom(String name) {
        return escapeRoomDAO.findByName(name);
    }

    public Optional<EscapeRoom> getEscapeRoom(Long id) {
        return escapeRoomDAO.findById(id);
    }

    public boolean deleteEscapeRoom(Long id) {
        return escapeRoomDAO.delete(id);
    }

    public boolean deleteEscapeRoom(String name) {
        Optional<EscapeRoom> escapeRoom = escapeRoomDAO.findByName(name);
        return escapeRoom.map(er -> escapeRoomDAO.delete(er.getId())).orElse(false);
    }

}

