package daos;

import dtos.EscapeRoomDTO;

import java.util.List;
import java.util.Optional;

public interface EscapeRoomDAO {
    EscapeRoomDTO save(EscapeRoomDTO escapeRoomDTO);
    Optional<EscapeRoomDTO> findByName(String name);
    List<EscapeRoomDTO> findAll();
    boolean delete(int id);
    Optional<EscapeRoomDTO> findById(int id);
}
