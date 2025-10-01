package repository.dao;

import repository.dto.DTO;

import java.util.List;
import java.util.Optional;

public interface DAO {
    DTO save(DTO DTO);
    Optional<DTO> findById(Integer id);
    DTO findByName(String name);
    List<DTO> findAll();
    boolean delete(Integer id);
}
