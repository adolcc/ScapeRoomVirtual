package services;

import configurations.DatabaseConfig;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public Connection getConnection() throws SQLException {
        return DatabaseConfig.getTestConnection();
    }

    public void cleanDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM escape_rooms");
            stmt.execute("ALTER TABLE escape_rooms AUTO_INCREMENT = 1");
        }
    }

    public void initializeDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS escape_room_test");
            stmt.execute("USE escape_rooms_test");

            executeInitScript(conn);

        } catch (Exception e) {
            throw new SQLException("Error inicializando la base de datos.");
        }
    }

    private void executeInitScript(Connection conn) {
        try {
            String initScript = new String(Files.readAllBytes(Paths.get("init.sql")));

            String[] statements = initScript.split(";");

            for (String statement : statements) {
                String trimmedStatement = statement.trim();
                if (!trimmedStatement.isEmpty()) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(trimmedStatement);
                    }
                }
            }
        } catch (Exception e) {
            createMinimalTable(conn);
        }
    }

    private void createMinimalTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS escape_rooms (\n" +
                    "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "name VARCHAR(255) NOT NULL UNIQUE,\n" +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                    ")\n");
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar hacer una estructura de tabla mínima.");
        }
    }

}
