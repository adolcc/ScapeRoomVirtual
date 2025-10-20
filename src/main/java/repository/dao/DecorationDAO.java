package repository.dao;

import exception.EmptyNameException;
import exception.PersistenceException;
import model.Decoration;
import repository.database.DatabaseConfig;
import repository.mapper.DecorationMapper;
import repository.mapper.GeneralMapper;

import java.sql.*;
import java.util.*;

public class DecorationDAO implements GenericDAO<Decoration, Long> {

    private final GeneralMapper<Decoration> mapper = DecorationMapper.getInstance();

    @Override
    public Decoration save(Decoration decoration) {
        mapper.validateEntity(decoration);

        String sql = "INSERT INTO decoration (name, material, price, room_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.
                     RETURN_GENERATED_KEYS)) {

            mapper.toPreparedStatement(decoration, stmt);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear la Decoración: no se modificó la tabla.");
            }

            setGeneratedId(decoration, stmt);

        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar la decoración: " + decoration.getName() + ".");
        }
        return decoration;
    }

    @Override
    public Optional<Decoration> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "SELECT id, name, material, price, room_id FROM decoration WHERE" +
                " id = ?";
        Optional<Decoration> decoration = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    decoration = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la decoración con ID = " + id);
        }
        return decoration;
    }

    @Override
    public Optional<Decoration> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyNameException();
        }

        String sql = "SELECT id, name, material, price, room_id FROM decoration WHERE" +
                " name = ?";
        Optional<Decoration> decoration = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    decoration = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la decoración por nombre: "
                    + name + ".");
        }
        return decoration;
    }

    @Override
    public List<Decoration> findAll() {
        String sql = "SELECT id, name, material, price, room_id FROM decoration";
        List<Decoration> decorations = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                decorations.add(mapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todas las decoraciones.");
        }
        return decorations;
    }

    public boolean roomAssignment(Long decorationId, Long roomId) {
        String sql = "UPDATE decoration SET room_id = ? WHERE id = ?";
        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, roomId);
            stmt.setLong(2, decorationId);

            affectedRows = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Error al asignar decoración a la sala.");
        }
        return affectedRows > 0;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "DELETE FROM decoration WHERE id = ?";

        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            affectedRows = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar la decoración con ID: " + id);
        }
        return affectedRows > 0;
    }

    private void setGeneratedId(Decoration decoration, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                decoration.setId(generatedKeys.getLong(1));
            } else {
                throw new PersistenceException("No se pudo obtener el ID generado para la decoración.");
            }
        }
    }
}
