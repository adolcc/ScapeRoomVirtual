package implementations;

import daos.EscapeRoomDAO;
import dtos.EscapeRoomDTO;
import exceptions.DuplicateEscapeRoomNameException;
import exceptions.EscapeRoomNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EscapeRoomDAOImpl implements EscapeRoomDAO {

    private final Connection connection;

    public EscapeRoomDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public EscapeRoomDTO save(EscapeRoomDTO escapeRoomDTO) {
        String sql = "INSERT INTO escape_rooms (name) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, escapeRoomDTO.getName());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ocurrió un fallo durante la creación del Escape Room -> " +
                        "no hubieron filas modificadas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    escapeRoomDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Ocurrió un fallo durante la creación del Escape Room -> " +
                            "no se pudo obtener el ID.");
                }
            }
            return escapeRoomDTO;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DuplicateEscapeRoomNameException();
            }
            throw new RuntimeException("Error al intentar guardar el Escape Room.");
        }
    }

    private EscapeRoomDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new EscapeRoomDTO(
                rs.getInt("id"),
                rs.getString("nombre")
        );
    }

    @Override
    public Optional<EscapeRoomDTO> findById(Integer id) {
        String sql = "SELECT id, name FROM escape_rooms WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToDTO(rs));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Escaper Room por ID.");
        }
    }

    @Override
    public EscapeRoomDTO findByName(String name) {
        String sql = "SELECT id, name FROM escape_rooms WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDTO(rs);
                } else {
                    throw new EscapeRoomNotFoundException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Escape Room por nombre.");
        }
    }

    @Override
    public List<EscapeRoomDTO> findAll() {
        String sql = "SELECT id, name FROM escape_rooms";
        List<EscapeRoomDTO> escapeRooms = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                escapeRooms.add(mapResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Escape Rooms.");
        }

        return escapeRooms;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM escape_rooms WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el Escape Room.");
        }
    }
}
