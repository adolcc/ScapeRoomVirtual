package repository.mapper;

import exception.EmptyNameException;
import exception.InvalidPriceException;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class RoomMapper implements GeneralMapper<Room> {

    private static final RoomMapper INSTANCE = new RoomMapper();

    public static RoomMapper getInstance() {return INSTANCE;}

    @Override
    public Room fromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new IllegalArgumentException("El ResultSet no puede ser nulo");
        }

        Room room = new Room(
                rs.getString("name"),
                rs.getInt(" difficulty_level"),
                rs.getDouble("price")
        );
        room.setId(rs.getLong("id"));
        Long escapeRoomId = rs.getLong("escape_room_id");
        if (!rs.wasNull()) {
            room.setEscapeRoomId(escapeRoomId);
        }

        return room;
    }

    @Override
    public void toPreparedStatement(Room room, PreparedStatement stmt) throws SQLException {
        if (stmt == null) {
            throw new IllegalArgumentException("El PreparedStatement no puede ser nulo");
        }
        validateEntity(room);

        stmt.setString(1, room.getName());
        stmt.setInt(2, room.getLevel());
        stmt.setDouble(3, room.getPrice());
        if (room.getEscapeRoomId() != null) {
            stmt.setLong(4, room.getEscapeRoomId());
        } else {
            stmt.setNull(4, Types.BIGINT);
        }
    }

    @Override
    public void validateEntity(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("La sala no puede ser nula");
        }
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            throw new EmptyNameException();
        }
        if (room.getPrice() <= 0) {
            throw new InvalidPriceException();
        }
        if (room.getLevel() < 1 || room.getLevel() > 5) {
            throw new IllegalArgumentException("El nivel de la sala debe estar entre 1 y 5");
        }
    }
}

