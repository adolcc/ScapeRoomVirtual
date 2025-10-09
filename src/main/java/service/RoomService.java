package service;


import exception.*;
import model.EscapeRoom;
import model.Room;
import repository.dao.EscapeRoomDAOImpl;
import repository.dao.GenericDAO;
import repository.dao.RoomDAOimpl;

import java.util.Optional;

public class RoomService {

    private GenericDAO<Room, Long> roomDAO;
    private GenericDAO<EscapeRoom, Long> escapeRoomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAOimpl();
        this.escapeRoomDAO = new EscapeRoomDAOImpl();
    }

    public RoomService(GenericDAO<Room, Long> roomDAO, GenericDAO<EscapeRoom, Long> escapeRoomDAO) {
        this.roomDAO = roomDAO;
        this.escapeRoomDAO = escapeRoomDAO;
    }

    public Room createAndValidateRoom(String name, int level) {
        if (name == null || name.isEmpty()) {
            throw new NullEscapeRoomNameException();
        }



        Optional<Room> existingRoom = roomDAO.findByName(name);
        if (existingRoom.isPresent()) {
            throw new DuplicateRoomNameException();
        }

        Room room = new Room(name, level);
        return roomDAO.save(room);
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