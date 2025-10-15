package service;


import exception.*;
import model.Decoration;
import model.Room;
import repository.dao.DecorationDAO;
import repository.dao.RoomDAO;
import java.util.List;
import java.util.Optional;

public class RoomService {

    private final RoomDAO roomDAO;
    private final DecorationDAO decorationDAO;

    public RoomService(){
        this.roomDAO = new RoomDAO();
        this.decorationDAO =new DecorationDAO();
    }

    public void checkNotNullName(String name) {
        if (name == null) {
            throw new NullEscapeRoomNameException();
        }
    }

    public void checkNotEmptyName(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyRoomNameException();
        }
    }

    public void checkNotDuplicateName(String name) {
       if (roomDAO.findByName(name).isPresent()) {
        throw new DuplicateRoomNameException();
        }
    }

    private void checkValidPrice(double price) {
        if (price < 0) {
            throw new InvalidPriceException();
        }
    }

    public Room createRoom(String name,int level, double price){
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        checkValidPrice(price);

        Room room = new Room (name.trim(), level, price);
        return roomDAO.save(room);
    }
    public Room createRoom(String name, int level){
        return  createRoom(name, level, 0.0);
    }
    public Decoration addDecorationToRoom(Long decorationId, Long roomId) {
        Room room = roomDAO.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        Decoration decoration = decorationDAO.findById(decorationId)
                .orElseThrow(DecorationNotFoundException::new);

        decoration.setRoomId(roomId);
        return decorationDAO.save(decoration);
    }

    public Decoration removeDecorationFromRoom(Long roomId, Long decorationId) {
        Room room = roomDAO.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        Decoration decoration = decorationDAO.findById(decorationId)
                .orElseThrow(DecorationNotFoundException::new);

        if (!roomId.equals(decoration.getRoomId())) {
            throw new IllegalArgumentException("La decoración indicada no está asociada a la sala solicitada.");
        }

        decoration.setRoomId(null);
        return decorationDAO.save(decoration);
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
