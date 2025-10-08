package repository.dao;

import model.Decoration;

import java.util.*;

public class DecorationDAOImpl implements GenericDAO<Decoration, Long> {

    private final Map<Long, Decoration> database = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Decoration save(Decoration decoration) {
        if (decoration.getId() == null) {
            decoration.setId(nextId++);
        }
        database.put(decoration.getId(), decoration);
        return decoration;
    }

    @Override
    public Optional<Decoration> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Decoration> findByName(String name) {
        return database.values().stream()
                .filter(decoration -> decoration.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Decoration> findAll() {
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
