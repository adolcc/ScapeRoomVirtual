package repository.dao;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import exception.PersistenceException;
import model.EscapeRoom;
import repository.database.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EscapeRoomDAOImpl implements GenericDAO<EscapeRoom, Long> {

    @Override
    public EscapeRoom save(EscapeRoom escapeRoom) {
        String sql = "INSERT INTO escape_room (name) VALUES (?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.
                     RETURN_GENERATED_KEYS)) {

            stmt.setString(1, escapeRoom.getName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear el Escape Room: no se modificó la tabla.");
            }

            setGeneratedId(escapeRoom, stmt);
            return escapeRoom;

        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar el Escape Room: " + escapeRoom.getName() + ".");
        }
    }


    @Override
    public Optional<EscapeRoom> findById(Long id) {
        String sql = "SELECT id, name FROM escape_room WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return executeQueryAndMapToEscapeRoom(stmt);

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar el Escape Room con ID = " + id + ".");
        }
    }

    @Override
    public Optional<EscapeRoom> findByName(String name) {
        String sql = "SELECT id, name FROM escape_room WHERE name = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            return executeQueryAndMapToEscapeRoom(stmt);

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar Escaper Room por nombre: " + name + ".");
        }
    }


    @Override
    public List<EscapeRoom> findAll() {
        String sql = "SELECT id, name FROM escape_room";
        List<EscapeRoom> escapeRooms = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
               EscapeRoom escapeRoom = mapResultSetToEscapeRoom(rs);
               escapeRooms.add(escapeRoom);
            }
            return escapeRooms;

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todos los Escape Rooms.");
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM escape_room WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar el escape room.");
        }
    }

    private void setGeneratedId(EscapeRoom escapeRoom, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                escapeRoom.setId(generatedKeys.getLong(1));
            } else {
                throw new OperationNotSupportedException("Error al intentar recuperar el ID generado para el Escape Room.");
            }
        }
    }

    private Optional<EscapeRoom> executeQueryAndMapToEscapeRoom(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(mapResultSetToEscapeRoom(rs));
            }
            return Optional.empty();
        }
    }

    private EscapeRoom mapResultSetToEscapeRoom(ResultSet rs) throws SQLException {
        EscapeRoom escapeRoom = new EscapeRoom(rs.getString("name"));
        escapeRoom.setId(rs.getLong("id"));
        return escapeRoom;
    }


}
