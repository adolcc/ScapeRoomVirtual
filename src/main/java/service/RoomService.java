package service;


import exception.*;
import model.EscapeRoom;
import model.Room;
import repository.dao.GenericDAO;

import java.util.Optional;

public class RoomService {

    private GenericDAO<Room, Long> roomDAO;
    private GenericDAO<EscapeRoom, Long> escapeRoomDAO;

    public RoomService() {
    }

    public RoomService(GenericDAO<Room, Long> roomDAO, GenericDAO<EscapeRoom, Long> escapeRoomDAO) {
        this.roomDAO = roomDAO;
        this.escapeRoomDAO = escapeRoomDAO;
    }

    public Room createAndValidateRoom(String name, int level) {
        if (name == null) {
            throw new NullEscapeRoomNameException();
        }

        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new EmptyRoomNameException();
        }

        Optional<Room> existingRoom = roomDAO.findByName(trimmedName);
        if (existingRoom.isPresent()) {
            throw new DuplicateRoomNameException();
        }

        Room room = new Room(trimmedName, level);
        return roomDAO.save(room);
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

        validateRoomForEscapeRoom(room);

        escapeRoom.addRoom(room);
        escapeRoomDAO.save(escapeRoom);
    }

    public void validateRoomForEscapeRoom(Room room) {
        if (room.getClues() == null || room.getClues().size() < 2) {
            throw new InsufficientCluesException();
        }
        if (room.getDecorations() == null || room.getDecorations().size() < 2) {
            throw new InsufficientDecorationsException();
        }
    }

    public Optional<Room> findRoomByName(String name) {
        return roomDAO.findByName(name);
    }

    public Optional<Room> findRoomById(Long id) {
        return roomDAO.findById(id);
    }

    public boolean deleteRoom(Long id) {
        return roomDAO.delete(id);
    }

    public java.util.List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public GenericDAO<Room, Long> getRoomDAO() {
        return roomDAO;
    }

    public void setRoomDAO(GenericDAO<Room, Long> roomDAO) {
        this.roomDAO = roomDAO;
    }

    public GenericDAO<EscapeRoom, Long> getEscapeRoomDAO() {
        return escapeRoomDAO;
    }

    public void setEscapeRoomDAO(GenericDAO<EscapeRoom, Long> escapeRoomDAO) {
        this.escapeRoomDAO = escapeRoomDAO;
    }
}