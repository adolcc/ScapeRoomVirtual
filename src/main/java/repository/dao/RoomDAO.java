package repository.dao;

import model.Room;
import exception.PersistenceException;
import repository.database.DatabaseConfig;
import repository.mapper.RoomMapper;
import repository.mapper.GeneralMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDAO implements GenericDAO<Room, Long> {

    private final GeneralMapper<Room> mapper = RoomMapper.getInstance();

    @Override
    public Room save(Room room) {
        String sql = "INSERT INTO room (name, difficulty_level, price,escape_room_id) VALUES (?, ?, ?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            mapper.toPreparedStatement(room, stmt);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear la sala: no se modificó la tabla.");
            }

            setGeneratedId(room, stmt);


        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar la sala: " + room.getName() + ".");
        }
        return room;
    }

    @Override
    public Optional<Room> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id debe ser un numero positivo.");
        }

        String sql = "SELECT id, name, difficulty_level, price, escape_room_id FROM room WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapper.fromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la sala con ID = " + id + ".");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Room> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        String sql = "SELECT id, name, difficulty_level, price , escape_room_id FROM room WHERE name = ?";


        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar sala por nombre: " + name + ".");
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        String sql = "SELECT id, name, difficulty_level, price,escape_room_id FROM room";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapper.fromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todas las salas.");
        }
        return rooms;
    }

    public boolean escapeRoomAssignment(Long roomId, Long escapeRoomId) {
        String sql = "UPDATE room SET escape_room_id = ? WHERE id = ?";
        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, escapeRoomId);
            stmt.setLong(2, roomId);

            affectedRows = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Error al asignar sala al escape room.");
        }
        return affectedRows > 0;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }
        String sql = "DELETE FROM room WHERE id = ?";
        int affectedRows = 0;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            affectedRows = stmt.executeUpdate();


        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar la sala.");
        }
        return affectedRows > 0;
    }

    private void setGeneratedId(Room room, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                room.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Error al intentar recuperar el ID generado para la sala.");
            }
        }
    }

    public List<Room> findByEscapeRoomId(Long escapeRoomId) {
        if (escapeRoomId == null || escapeRoomId <= 0) {
            throw new IllegalArgumentException("El escapeRoomId debe ser un número positivo.");
        }
        String sql = "SELECT id, name, difficulty_level, price, escape_room_id FROM room WHERE escape_room_id = ?";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, escapeRoomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = mapper.fromResultSet(rs);
                rooms.add(room);
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar salas por escape room ID: " + escapeRoomId);
        }
        return rooms;
    }
}

