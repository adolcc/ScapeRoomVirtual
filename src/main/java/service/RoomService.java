package service;


import exception.*;
import model.Decoration;
import model.Room;

import java.util.HashSet;
import java.util.Set;

public class RoomService {

    private Set<Room> roomSet;

    public RoomService() {
        this.roomSet = new HashSet<>();
    }

    public void checkNotNullName(String name) {
        if (name == null) {
            throw new NullEscapeRoomNameException();
        }
    }

    public void checkNotEmptyName(String name) {
        name = name.trim();
        if (name.isEmpty()) {
            throw new EmptyRoomNameException();
        }
    }

    public void checkNotDuplicateName(String name) {
        if (roomSet.contains(new Room(name, 1))) { // nivel ficticio para comparación
            throw new DuplicateRoomNameException();
        }
    }

    public void createRoom(String name, int level) {
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        roomSet.add(new Room(name.trim(), level));
    }
// TODO: esperando implementación de RoomDAO
//
//    public Decoration addDecorationToRoom(Long decorationId, Long roomId) {
//        Room room = roomDAO.findById(roomId)
//                .orElseThrow(() -> new RoomNotFoundException());
//
//        Decoration decoration = decorationDAO.findById(decorationId)
//                .orElseThrow(() -> new DecorationNotFoundException());
//
//        decoration.setRoomId(roomId);
//        return decorationDAO.save(decoration);
//    }
//
//    public Decoration removeDecorationFromRoom(Long roomId, Long decorationId) {
//        Room room = roomDAO.findById(roomId)
//                .orElseThrow(() -> new RoomNotFoundException());
//
//        Decoration decoration = decorationDAO.findById(decorationId)
//                .orElseThrow(() -> new DecorationNotFoundException());
//
//        if (!roomId.equals(decoration.getRoomId())) {
//            throw new IllegalArgumentException("La decoración indicada no está asociada a la sala solicitada.");
//        }
//
//        decoration.setRoomId(null);
//        return decorationDAO.save(decoration);
//    }
//
//    public List<Decoration> getRoomDecoration(Long roomId) { necesario ¿?
//        decorationDAO.findByRoomId(roomId);
//    }

    public Set<Room> getRooms() {
        return roomSet;
    }
}
