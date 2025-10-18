package repository.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface GeneralMapper<T> {

    T fromResultSet(ResultSet rs) throws SQLException;

    void toPreparedStatement(T entity, PreparedStatement stmt) throws SQLException;

    void validateEntity(T entity);

}
