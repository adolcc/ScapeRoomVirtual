package service;

import exception.*;
import model.EscapeRoom;
import model.Room;
import repository.dao.EscapeRoomDAOImpl;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

public class EscapeRoomService {

    private final GenericDAO<EscapeRoom, Long> escapeRoomDAO;

    public EscapeRoomService() {
        this.escapeRoomDAO = new EscapeRoomDAOImpl();
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

    public void addRoomToEscapeRoom(String escapeRoomName, Room room) {
        EscapeRoom escapeRoom = escapeRoomDAO.findByName(escapeRoomName)
                .orElseThrow(EscapeRoomNotFoundException::new);

        if (escapeRoom.getRooms().contains(room)) {
            throw new DuplicateRoomNameException();
        }
        if (room.getClues().size() < 2) {
            throw new InsufficientCluesException();
        }
        if (room.getDecorations().size() < 2) {
            throw new InsufficientDecorationsException();
        }

        escapeRoom.addRoom(room);
        escapeRoomDAO.save(escapeRoom);
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

