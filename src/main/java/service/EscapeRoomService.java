package service;

import exception.*;
import model.EscapeRoom;
import model.Room;
import model.RoomAssets;
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
            loadRooms(escapeRoom);
        }
    }

    private void loadRooms(EscapeRoom escapeRoom) {
        RoomDAO roomDAO1 = (RoomDAO) roomDAO;
        List<Room> rooms = roomDAO1.findByEscapeRoomId(escapeRoom.getId());
        escapeRoom.setRooms(rooms);
    }

    public List<EscapeRoom> getEscapeRooms() {
        List<EscapeRoom> escapeRooms = escapeRoomDAO.findAll();
        escapeRooms.forEach(this::loadRooms);
        return escapeRooms;
    }

    public Optional<EscapeRoom> getEscapeRoom(String name) {
        Optional<EscapeRoom> escapeRoomOpt = escapeRoomDAO.findByName(name);
        escapeRoomOpt.ifPresent(this::loadRooms);
        return escapeRoomOpt;
    }

    public Optional<EscapeRoom> getEscapeRoom(Long id) {
        Optional<EscapeRoom> escapeRoomOpt = escapeRoomDAO.findById(id);
        escapeRoomOpt.ifPresent(this::loadRooms);
        return escapeRoomOpt;
    }

    public boolean deleteEscapeRoom(Long id) {
        return escapeRoomDAO.delete(id);
    }

    public boolean deleteEscapeRoom(String name) {
        Optional<EscapeRoom> escapeRoom = escapeRoomDAO.findByName(name);
        return escapeRoom.map(er -> escapeRoomDAO.delete(er.getId())).orElse(false);
    }

}

