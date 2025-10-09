package service;

import exception.*;
import model.EscapeRoom;
import model.Room;
import repository.dao.EscapeRoomDAOImpl;
import repository.dao.GenericDAO;
import repository.dao.RoomDAOimpl;

import java.util.List;
import java.util.Optional;

public class EscapeRoomService {

    private final GenericDAO<EscapeRoom, Long> escapeRoomDAO;
    private RoomDAOimpl roomDAO;
    private RoomService roomService;

    public EscapeRoomService() {
        this.escapeRoomDAO = new EscapeRoomDAOImpl();
        this.roomDAO =new RoomDAOimpl();
        this.roomService = new RoomService();

    }

    public EscapeRoomService(GenericDAO<EscapeRoom, Long> escapeRoomDAO) {
        this.escapeRoomDAO = escapeRoomDAO;
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
    public void addRoomToEscapeRoom(String escapeRoomName, Room room) {
        if (escapeRoomDAO == null || roomDAO == null) {
            throw new IllegalStateException("DAOs no han sido inicializados");
        }

        Optional<EscapeRoom> escapeRoomOpt = escapeRoomDAO.findByName(escapeRoomName);
        if (escapeRoomOpt.isEmpty()) {
            throw new EscapeRoomNotFoundException();
        }

        EscapeRoom escapeRoom = escapeRoomOpt.get();

        if (escapeRoom.getRooms().contains(room)) {
            throw new DuplicateRoomNameException();
        }

        roomService.validateRoomForEscapeRoom(room);

        escapeRoom.addRoom(room);
        escapeRoomDAO.save(escapeRoom);
        roomDAO.save(room);
    }
}


