package repository.dao;

import exception.PersistenceException;
import model.Decoration;
import repository.database.DatabaseConfig;

import java.sql.*;
import java.util.*;

public class DecorationDAOImpl implements GenericDAO<Decoration, Long> {

    @Override
    public Decoration save(Decoration decoration) {
        String sql = "INSERT INTO decoration (name, material, price, room_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.
                     RETURN_GENERATED_KEYS)) {

            stmt.setString(1, decoration.getName());
            stmt.setString(2, decoration.getMaterial());
            stmt.setDouble(3, decoration.getPrice());

            if (decoration.getRoomId() != null) {
                stmt.setLong(4, decoration.getRoomId());
            } else {
                stmt.setNull(4, Types.BIGINT);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear la Decoración: no se modificó la tabla.");
            }

            setGeneratedId(decoration, stmt);
            return decoration;

        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar la decoración: " + decoration.getName() + ".");
        }
    }

    @Override
    public Optional<Decoration> findById(Long id) {
        String sql = "SELECT id, name, material, price, room_id FROM decoration WHERE" +
                " id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return executeQueryAndMapToDecoration(stmt);
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la decoración con ID = " + id);
        }
    }

    @Override
    public Optional<Decoration> findByName(String name) {
        String sql = "SELECT id, name, material, price, room_id FROM decoration WHERE" +
                " name = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            return executeQueryAndMapToDecoration(stmt);

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la decoración por nombre: "
                    + name + ".");
        }
    }

    @Override
    public List<Decoration> findAll() {
        String sql = "SELECT id, name, material, price, room_id FROM decoration";
        List<Decoration> decorations = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                decorations.add(mapResultSetToDecoration(rs));
            }
            return decorations;
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todas las decoraciones.");
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM decoration WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar la decoración con ID: " + id);
        }
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

    private Optional<Decoration> executeQueryAndMapToDecoration(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(mapResultSetToDecoration(rs));
            }
            return Optional.empty();
        }
    }

    private Decoration mapResultSetToDecoration(ResultSet rs) throws SQLException {
        Decoration decoration = new Decoration(
                rs.getString("name"),
                rs.getString("material"),
                rs.getDouble("price")
        );
        decoration.setId(rs.getLong("id"));

        Long roomId = rs.getLong("room_id");
        if (!rs.wasNull()) {
            decoration.setRoomId(roomId);
        } else {
            decoration.setRoomId(null);
        }
        return decoration;
    }
}
