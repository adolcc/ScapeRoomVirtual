package configurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        URL = "jdbc:mysql://localhost:3306/escape_room_db";
        USER = "escape_user";
        PASSWORD = "escape_password";

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/escape_room_test",
                USER,
                PASSWORD);
    }
}
