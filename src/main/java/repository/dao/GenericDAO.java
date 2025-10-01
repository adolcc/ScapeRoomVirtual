package repository.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    Optional<T> findByName(String name);

    List<T> findAll();

    boolean delete(ID id);
}
