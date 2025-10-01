package repository.dao;

import model.EscapeRoom;

import java.util.*;

public class EscapeRoomDAOImpl implements GenericDAO<EscapeRoom, Long> {

    private final Map<Long, EscapeRoom> database = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public EscapeRoom save(EscapeRoom escapeRoom) {
        if (escapeRoom.getId() == null) {
            escapeRoom.setId(nextId++);
        }
        database.put(escapeRoom.getId(), escapeRoom);
        return escapeRoom;
    }

    @Override
    public Optional<EscapeRoom> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<EscapeRoom> findByName(String name) {
        return database.values().stream()
                .filter(escapeRoom -> escapeRoom.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<EscapeRoom> findAll() {
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
