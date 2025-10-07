package repository.dao;



import model.Room;
import java.util.*;

public class RoomDAOimpl implements GenericDAO<Room, Long> {

    private final Map<Long, Room> database = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Room save(Room room) {
        if (room.getId() == null) {
            room.setId(nextId++);
        }
        database.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Room> findByName(String name) {
        return database.values().stream()
                .filter(room -> room.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public boolean delete(Long id) {
        if (database.containsKey(id)) {
            database.remove(id);
            return true;
        }
        return false;
    }
}