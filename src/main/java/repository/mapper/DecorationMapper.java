package repository.mapper;

import constant.FieldName;
import exception.factory.ExceptionFactory;
import model.Decoration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DecorationMapper implements GeneralMapper<Decoration> {

    private static final DecorationMapper INSTANCE = new DecorationMapper();

    public static DecorationMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Decoration fromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new IllegalArgumentException("El set no puede estar vacío.");
        }

        Decoration decoration = new Decoration(
                rs.getString("name"),
                rs.getString("material"),
                rs.getDouble("price")
        );
        decoration.setId(rs.getLong("id"));

        Long roomId = rs.getLong("room_id");
        if (!rs.wasNull()) {
            decoration.setRoomId(roomId);
        }
        return decoration;
    }

    @Override
    public void toPreparedStatement(Decoration decoration, PreparedStatement stmt) throws SQLException {
        if (stmt == null) {
            throw new IllegalArgumentException("La declaración no puede estar vacía.");
        }
        validateEntity(decoration);

        stmt.setString(1, decoration.getName());
        stmt.setString(2, decoration.getMaterial());
        stmt.setDouble(3, decoration.getPrice());

        if (decoration.getRoomId() != null) {
            stmt.setLong(4, decoration.getRoomId());
        } else {
            stmt.setNull(4, Types.BIGINT);
        }
    }

    @Override
    public void validateEntity(Decoration decoration) {
        if (decoration == null) {
            throw new IllegalArgumentException("La decoración no puede estar vacía.");
        }
        if (decoration.getName() == null || decoration.getName().trim().isEmpty()
                || decoration.getMaterial() == null || decoration.getMaterial().trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        if (decoration.getPrice() < 0) {
            throw ExceptionFactory.invalidPrice();
        }
    }
}
