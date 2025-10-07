
/*
package repository.implementation;

import model.EscapeRoom;
import model.Room;
import repository.dao;
import repository.dto.DTO;
import exception.DuplicateEscapeRoomNameException;
import exception.EscapeRoomNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static repository.database.DatabaseConfig.getConnection;

public class DAOImpl implements DAO {

    private final Connection connection;

    public DAOImpl(Connection connection) {
        this.connection = connection;
    }

    public DTO save(EscapeRoom DTO) {
        String sql = "INSERT INTO escape_rooms (name) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, DTO.getName());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ocurrió un fallo durante la creación del Escape Room -> " +
                        "no hubieron filas modificadas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    DTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Ocurrió un fallo durante la creación del Escape Room -> " +
                            "no se pudo obtener el ID.");
                }
            }
            return DTO;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DuplicateEscapeRoomNameException();
            }
            throw new RuntimeException("Error al intentar guardar el Escape Room.");
        }
    }

    private DTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new DTO(
                rs.getInt("id"),
                rs.getString("nombre")
        );
    }

    @Override
    public Optional<DTO> findById(Integer id) {
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
    public DTO findByName(String name) {
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
    public List<DTO> findAll() {
        String sql = "SELECT id, name FROM escape_rooms";
        List<DTO> escapeRooms = new ArrayList<>();

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
    // MÉTODOS PARA ROOM
    public void saveRoom(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (name, level, escape_room_name) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, room.getLevel());
            stmt.setString(3, "La Prisión"); // O el nombre del escape room correspondiente
            stmt.executeUpdate();
        }
    }

    public List<Room> findAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT name, level FROM rooms";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Room room = new Room(rs.getString("name"), rs.getInt("level"));
                rooms.add(room);
            }
        }
        return rooms;
    }

    public Optional<Room> findRoomByName(String name) throws SQLException {
        String sql = "SELECT name, level FROM rooms WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room(rs.getString("name"), rs.getInt("level"));
                    return Optional.of(room);
                }
            }
        }
        return Optional.empty();
    }

    public void deleteRoomByName(String name) throws SQLException {
        String sql = "DELETE FROM rooms WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
}
*/
