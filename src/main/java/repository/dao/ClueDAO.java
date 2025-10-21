package repository.dao;

import constant.FieldName;
import exception.core.PersistenceException;
import exception.factory.ExceptionFactory;
import model.Clue;
import repository.database.DatabaseConfig;
import repository.mapper.ClueMapper;
import repository.mapper.GeneralMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClueDAO implements GenericDAO<Clue, Long> {

    private final GeneralMapper<Clue> mapper = ClueMapper.getInstance();

    @Override
    public Clue save(Clue clue) {
        mapper.validateEntity(clue);

        String sql = "INSERT INTO clue (name, price, room_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            mapper.toPreparedStatement(clue, stmt);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear la Pista: no se modificó la tabla.");
            }

            setGeneratedId(clue, stmt);

        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar la pista: " + clue.getName() + ".");
        }
        return clue;
    }

    @Override
    public Optional<Clue> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "SELECT id, name, price, room_id FROM clue WHERE id = ?";
        Optional<Clue> clue = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clue = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la pista con ID = " + id + ".");
        }
        return clue;
    }

    @Override
    public Optional<Clue> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }

        String sql = "SELECT id, name, price, room_id FROM clue WHERE name = ?";
        Optional<Clue> clue = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clue = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar pista por nombre: " + name + ".");
        }
        return clue;
    }

    @Override
    public List<Clue> findAll() {
        String sql = "SELECT id, name, price, room_id FROM clue";
        List<Clue> clues = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clues.add(mapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todas las pistas.");
        }
        return clues;
    }

    public boolean roomAssignment(Long clueId, Long roomId) {
        String sql = "UPDATE clue SET room_id = ? WHERE id = ?";
        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, roomId);
            stmt.setLong(2, clueId);

            affectedRows = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Error al asignar pista a la sala.");
        }
        return affectedRows > 0;
    }

    public List<Clue> findByRoomId(Long roomId) {
        String sql = "SELECT id, name, price, room_id FROM clue WHERE room_id = ?";
        List<Clue> clues =  new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clues.add(mapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar pistas por ID de sala " + roomId + ".");
        }
        return clues;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "DELETE FROM clue WHERE id = ?";
        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            affectedRows = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar la pista con ID: " + id);
        }
        return affectedRows > 0;
    }

    private void setGeneratedId(Clue clue, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                clue.setId(generatedKeys.getLong(1));
            } else {
                throw new PersistenceException("No se pudo obtener el ID generado para la pista.");
            }
        }
    }
}
