package implementations;

import daos.EscapeRoomDAO;
import dtos.EscapeRoomDTO;
import exceptions.DuplicateEscapeRoomNameException;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EscapeRoomDAOImpl implements EscapeRoomDAO {

    private final Connection connection;

    public EscapeRoomDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public EscapeRoomDTO save(EscapeRoomDTO escapeRoomDTO) {
        String sql = "INSERT INTO escape_rooms (name) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, escapeRoomDTO.getName());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ocurrió un fallo durante la creación del Escape Room -> " +
                        "no hubieron filas modificadas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    escapeRoomDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Ocurrió un fallo durante la creación del Escape Room -> " +
                            "no se pudo obtener el ID.");
                }
            }
            return escapeRoomDTO;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DuplicateEscapeRoomNameException();
            }
            throw new RuntimeException("Error al intentar guardar el Escape Room.");
        }
    }

    @Override
    public Optional<EscapeRoomDTO> findById(Integer id) {
        throw new UnsupportedOperationException("No implementado aún.");
    }

    @Override
    public Optional<EscapeRoomDTO> findByName(String name) {
        throw new UnsupportedOperationException("No implementado aún.");
    }

    @Override
    public List<EscapeRoomDTO> findAll() {
        throw new UnsupportedOperationException("No implementado aún.");
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("No implementado aún.");
    }
}
