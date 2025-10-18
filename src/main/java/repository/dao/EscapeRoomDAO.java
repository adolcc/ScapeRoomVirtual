package repository.dao;

import exception.EmptyNameException;
import exception.PersistenceException;
import model.EscapeRoom;
import repository.database.DatabaseConfig;
import repository.mapper.EscapeRoomMapper;
import repository.mapper.GeneralMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EscapeRoomDAO implements GenericDAO<EscapeRoom, Long> {

    private final GeneralMapper<EscapeRoom> mapper = EscapeRoomMapper.getInstance();

    @Override
    public EscapeRoom save(EscapeRoom escapeRoom) {
        mapper.validateEntity(escapeRoom);
        String sql = "INSERT INTO escape_room (name) VALUES (?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.
                     RETURN_GENERATED_KEYS)) {

            mapper.toPreparedStatement(escapeRoom, stmt);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear el Escape Room: no se modificó la tabla.");
            }

            setGeneratedId(escapeRoom, stmt);

        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar el Escape Room: " + escapeRoom.getName() + ".");
        }
        return escapeRoom;
    }

    @Override
    public Optional<EscapeRoom> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "SELECT id, name FROM escape_room WHERE id = ?";
        Optional<EscapeRoom> escapeRoom = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    escapeRoom = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar el Escape Room con ID = " + id + ".");
        }
        return escapeRoom;
    }

    @Override
    public Optional<EscapeRoom> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyNameException();
        }

        String sql = "SELECT id, name FROM escape_room WHERE name = ?";
        Optional<EscapeRoom> escapeRoom = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    escapeRoom = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar Escaper Room por nombre: " + name + ".");
        }
        return escapeRoom;
    }


    @Override
    public List<EscapeRoom> findAll() {
        String sql = "SELECT id, name FROM escape_room";
        List<EscapeRoom> escapeRooms = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                escapeRooms.add(mapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todos los Escape Rooms.");
        }
        return escapeRooms;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "DELETE FROM escape_room WHERE id = ?";
        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            affectedRows = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar el escape room.");
        }
        return affectedRows > 0;
    }

    private void setGeneratedId(EscapeRoom escapeRoom, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                escapeRoom.setId(generatedKeys.getLong(1));
            } else {
                throw new PersistenceException("No se pudo obtener el ID generado para el Escape Room.");
            }
        }
    }
}
