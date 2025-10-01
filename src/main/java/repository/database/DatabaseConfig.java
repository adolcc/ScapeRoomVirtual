package repository.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/escape_room_db";
    private static final String USER = "escape_user";
    private static final String PASSWORD = "escape_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando MySQL JDBC Driver.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/escape_room_test",
                USER,
                PASSWORD
        );
    }
}
