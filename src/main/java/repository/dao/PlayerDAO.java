package repository.dao;

import exception.EmptyNameException;
import exception.PersistenceException;
import model.Player;
import repository.database.DatabaseConfig;
import repository.mapper.GeneralMapper;
import repository.mapper.PlayerMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDAO implements GenericDAO<Player, Long> {

    private final GeneralMapper<Player> mapper = PlayerMapper.getInstance();

    @Override
    public Player save(Player player) {
        mapper.validateEntity(player);

        String sql = "INSERT INTO player (name, email) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            mapper.toPreparedStatement(player, stmt);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenceException("Error al crear al Jugador: no se modificó la tabla.");
            }
            setGeneratedId(player, stmt);
        } catch (SQLException e) {
            throw new PersistenceException("Error al guardar al jugador " + player.getName() + ".");
        }
        return player;
    }

    @Override
    public Optional<Player> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "SELECT id, name, email, newsletter_subscribed FROM player WHERE id = ?";
        Optional<Player> player = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    player = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar al jugar con ID = " + id);
        }
        return player;
    }

    @Override
    public Optional<Player> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyNameException();
        }

        String sql = "SELECT id, name, email, newsletter_subscribed FROM player WHERE name = ?";
        Optional<Player> player = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    player = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar el jugador por nombre: "
                    + name + ".");
        }
        return player;
    }

    public Optional<Player> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new EmptyNameException();
        }

        String sql = "SELECT id, name, email, newsletter_subscribed FROM player WHERE email = ?";
        Optional<Player> player = Optional.empty();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    player = Optional.of(mapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar el jugador por email: "
                    + email + ".");
        }
        return player;
    }

    @Override
    public List<Player> findAll() {
        String sql = "SELECT id, name, email, newsletter_subscribed FROM player";
        List<Player> players = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                players.add(mapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar todas las decoraciones.");
        }

        return players;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }

        String sql = "DELETE FROM player WHERE id = ?";

        int affectedRows;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            affectedRows = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error al borrar al jugador con ID: " + id);
        }
        return affectedRows > 0;
    }

    private void setGeneratedId(Player player, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                player.setId(generatedKeys.getLong(1));
            } else {
                throw new PersistenceException("No se pudo obtener el ID generado para el jugador.");
            }
        }
    }
}