package repository.mapper;

import constant.FieldName;
import exception.factory.ExceptionFactory;
import model.EscapeRoom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EscapeRoomMapper implements GeneralMapper<EscapeRoom> {

    private static final EscapeRoomMapper INSTANCE = new EscapeRoomMapper();

    public static EscapeRoomMapper getInstance() { return INSTANCE;}

    @Override
public EscapeRoom fromResultSet(ResultSet rs) throws SQLException {
        if(rs == null) {
            throw new IllegalArgumentException("El set no puede estar vacío.");
        }

        EscapeRoom escapeRoom = new EscapeRoom(
                rs.getString("name")
        );
        escapeRoom.setId(rs.getLong("id"));
        return escapeRoom;
    }

    @Override
    public void toPreparedStatement(EscapeRoom escapeRoom, PreparedStatement stmt) throws SQLException {
        if (stmt == null) {
            throw new IllegalArgumentException("La declaración no puede estar vacía.");
        }
        validateEntity(escapeRoom);

        stmt.setString(1, escapeRoom.getName());
    }

    @Override
    public void validateEntity(EscapeRoom escapeRoom) {
        if (escapeRoom == null || escapeRoom.getName() == null || escapeRoom.getName().trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
    }




}
