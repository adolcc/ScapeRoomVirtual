package repository.dao;

import model.Clue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClueDAO implements GenericDAO<Clue, Long> {

    private final Map<Long, Clue> clueDB = new HashMap<>();
    private long nextId = 1;

    @Override
    public Clue save(Clue entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La pista no puede ser nula");
        }

        if (entity.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio de la pista debe ser mayor que cero");
        }

        if (entity.getEscapeRoom() == null) {
            throw new IllegalArgumentException("La pista debe estar asociada a una sala");
        }

        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        clueDB.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Clue> findById(Long id) {
        return Optional.ofNullable(clueDB.get(id));
    }

    @Override
    public Optional<Clue> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return clueDB.values()
                .stream()
                .filter(clue -> name.equals(clue.getName()))
                .findFirst();
    }

    @Override
    public List<Clue> findAll() {
        return new ArrayList<>(clueDB.values());
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || !clueDB.containsKey(id)) {
            return false;
        }
        clueDB.remove(id);
        return true;
    }
}