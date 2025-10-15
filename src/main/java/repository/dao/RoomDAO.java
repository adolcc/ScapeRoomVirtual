package repository.dao;

import model.Room;
import exception.PersistenceException;
import repository.database.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    public class RoomDAO implements GenericDAO<Room, Long> {

        @Override
        public Room save(Room room) {
            String sql = "INSERT INTO room (name, difficulty_level, price) VALUES (?, ?, ?)";

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, room.getName());
                stmt.setInt(2, room.getDifficultyLevel());
                stmt.setDouble(3, room.getPrice());
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new PersistenceException("Error al crear la sala: no se modificó la tabla.");
                }

                setGeneratedId(room, stmt);
                return room;

            } catch (SQLException e) {
                throw new PersistenceException("Error al guardar la sala: " + room.getName() + ".");
            }
        }

        @Override
        public Optional<Room> findById(Long id) {
            String sql = "SELECT id, name, difficulty_level, price FROM room WHERE id = ?";

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setLong(1, id);
                return executeQueryAndMapToRoom(stmt);

            } catch (SQLException e) {
                throw new PersistenceException("Error al buscar la sala con ID = " + id + ".");
            }
        }

        @Override
        public Optional<Room> findByName(String name) {
            String sql = "SELECT id, name, difficulty_level, price FROM room WHERE name = ?";

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, name);
                return executeQueryAndMapToRoom(stmt);

            } catch (SQLException e) {
                throw new PersistenceException("Error al buscar sala por nombre: " + name + ".");
            }
        }

        @Override
        public List<Room> findAll() {
            String sql = "SELECT id, name, difficulty_level, price FROM room";
            List<Room> rooms = new ArrayList<>();

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Room room = mapResultSetToRoom(rs);
                    rooms.add(room);
                }
                return rooms;

            } catch (SQLException e) {
                throw new PersistenceException("Error al buscar todas las salas.");
            }
        }

        @Override
        public boolean delete(Long id) {
            String sql = "DELETE FROM room WHERE id = ?";

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setLong(1, id);
                int affectedRows = stmt.executeUpdate();
                return affectedRows > 0;

            } catch (SQLException e) {
                throw new PersistenceException("Error al borrar la sala.");
            }
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

        private Optional<Room> executeQueryAndMapToRoom(PreparedStatement stmt) throws SQLException {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRoom(rs));
                }
                return Optional.empty();
            }
        }

        private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
            Room room = new Room(rs.getString("name"), rs.getInt("difficulty_level"));
            room.setId(rs.getLong("id"));
            return room;
        }
    }