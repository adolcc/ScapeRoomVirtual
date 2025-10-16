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

    private void checkNotNullName(String name) {
        if (name == null) {
            throw new NullEscapeRoomNameException();
        }
    }

    private void checkNotEmptyName(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyRoomNameException();
        }
    }

    private void checkNotDuplicateName(String name) {
       if (roomDAO.findByName(name).isPresent()) {
        throw new DuplicateRoomNameException();
        }
    }

    private void checkValidPrice(double price) {
        if (price <= 0) {
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
    public Decoration addDecorationToRoom(String roomName, String decorationName) {
        Room room = roomDAO.findByName(roomName)
                .orElseThrow(() -> new RoomNotFoundException("Sala inexistente");

        Decoration decoration = decorationDAO.findByNAme(decorationName)
                .orElseThrow(() -> new DecorationNotFoundException("Decoración no encontrada");
        Long roomId = room.getId();
        decoration.setRoomId(roomId);
        return decorationDAO.save(decoration);
    }
    public decoration removeDecorationFromRoom(String roomName, String decorationName) {
        Room room = roomDAO.findByName(roomName).orElseThrow(RoomNotFoundException::new);
        Decoration decoration = decorationDAO.findByName(decorationName).orElseThrow(DecorationNotfoundException::new);
    if (!decoration.getRoomId().equals(room.getId)){
        throw new IlegalArgumentException("La decoracion indicada no está asociada a la sala solicitada.");
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
