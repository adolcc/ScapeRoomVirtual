package service;


import exception.*;
import model.Decoration;
import model.Room;
import repository.dao.DecorationDAO;
import repository.dao.RoomDAO;
import java.util.List;


public class RoomService {

    private RoomDAO roomDAO;
    private DecorationDAO decorationDAO;

    public RoomService(RoomDAO roomDAO, DecorationDAO decorationDAO){
        this.roomDAO = roomDAO;
        this.decorationDAO = decorationDAO;
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
       if (roomDAO.findByName(name).isPresent()) {
        throw new DuplicateRoomNameException();
        }
    }

    public Room createRoom(String name,int level, double price){
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        Room room = new Room (name.trim(), level, price);
        return roomDAO.save(room);
    }
    public Decoration addDecorationToRoom(Long decorationId, Long roomId) {
        Room room = roomDAO.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        Decoration decoration = decorationDAO.findById(decorationId)
                .orElseThrow();

        decoration.setRoomId(roomId);
        return decorationDAO.save(decoration);
    }

    public Decoration removeDecorationFromRoom(Long roomId, Long decorationId) {
        Room room = roomDAO.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        Decoration decoration = decorationDAO.findById(decorationId)
                .orElseThrow();

        if (!roomId.equals(decoration.getRoomId())) {
            throw new IllegalArgumentException("La decoración indicada no está asociada a la sala solicitada.");
        }

        decoration.setRoomId(null);
        return decorationDAO.save(decoration);
    }

    public List<Room> getRooms() {
        return roomDAO.findAll();
    }
}
