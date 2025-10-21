package repository.mapper;

import constant.FieldName;
import exception.factory.ExceptionFactory;
import model.Clue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ClueMapper implements GeneralMapper<Clue> {

    private static final ClueMapper INSTANCE = new ClueMapper();

    public static ClueMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Clue fromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new IllegalArgumentException("El set no puede estar vacío.");
        }

        Clue clue = new Clue(
                rs.getString("name"),
                rs.getDouble("price")
        );
        clue.setId(rs.getLong("id"));

        Long roomId = rs.getLong("room_id");
        if (!rs.wasNull()) {
            clue.setRoomId(roomId);
        }
        return clue;
    }

    @Override
    public void toPreparedStatement(Clue clue, PreparedStatement stmt) throws SQLException {
        if (stmt == null) {
            throw new IllegalArgumentException("La declaración no puede estar vacía.");
        }
        validateEntity(clue);

        stmt.setString(1, clue.getName());
        stmt.setDouble(2, clue.getPrice());

        if (clue.getRoomId() != null) {
            stmt.setLong(3, clue.getRoomId());
        } else {
            stmt.setNull(3, Types.BIGINT);
        }
    }

    @Override
    public void validateEntity(Clue clue) {
        if (clue == null) {
            throw new IllegalArgumentException("La pista no puede estar vacía.");
        }
        if (clue.getName() == null || clue.getName().trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        if (clue.getPrice() < 0) {
            throw ExceptionFactory.invalidPrice();
        }
    }
}

