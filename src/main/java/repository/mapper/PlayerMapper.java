package repository.mapper;

import exception.EmptyNameException;
import model.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PlayerMapper implements GeneralMapper<Player> {

    private static final PlayerMapper INSTANCE = new PlayerMapper();

    public static PlayerMapper getInstance() { return INSTANCE; }

    @Override
    public Player fromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
         throw new IllegalArgumentException("El set no puede estar vacío.");
        }

        Player player = new Player(
                rs.getString("name"),
                rs.getString("email")
        );
        player.setId(rs.getLong("id"));
    return player;
    }

    @Override
    public void toPreparedStatement(Player player, PreparedStatement stmt) throws SQLException {
        if (stmt == null) {
            throw new IllegalArgumentException("El jugador no puede estar vacío.");
        }
        validateEntity(player);

        stmt.setString(1, player.getName());
        stmt.setString(2, player.getEmail());
        stmt.setNull(3, Types.BIGINT);
    }

    @Override
    public void validateEntity(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("El jugador no puede estar vacío.");
        }
        if (player.getName() == null || player.getName().trim().isEmpty()
        || player.getEmail() == null || player.getEmail().trim().isEmpty()) {
            throw new EmptyNameException();
        }
    }
}
