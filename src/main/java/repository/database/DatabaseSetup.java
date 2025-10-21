package repository.database;

import exception.PersistenceException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public DatabaseSetup() {
        try {
            initializeDatabase();
    } catch (SQLException e) {
            throw new PersistenceException("Advertencia: no se pudo inicializar la BD.");
        }
    }

    public Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }

    public void cleanDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM decoration");
            stmt.execute("DELETE FROM clue");
            stmt.execute("DELETE FROM room");
            stmt.execute("DELETE FROM escape_room");
            stmt.execute("DELETE FROM player");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    public void initializeDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS escape_room_db");
            stmt.execute("USE escape_room_db");

            executeInitScript(conn);

        } catch (Exception e) {
            throw new SQLException("Error inicializando la base de datos.");
        }
    }

    private void executeInitScript(Connection conn) {
        try {
            String initScript = new String(Files.readAllBytes(Paths.get("src/main/resources/init.sql")));

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
            createMinimalTables(conn);
        }
    }

    private void createMinimalTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS escape_room (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL UNIQUE, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.execute("CREATE TABLE IF NOT EXISTS room (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "escape_room_id BIGINT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "difficulty_level INT, " +
                    "price DECIMAL(10,2), " +
                    "FOREIGN KEY (escape_room_id) REFERENCES escape_room(id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS clue (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "room_id BIGINT, " +
                    "price DECIMAL(10,2), " +
                    "FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS decoration (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "room_id BIGINT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "material VARCHAR(255), " +
                    "price DECIMAL(10,2), " +
                    "FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS player (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL UNIQUE, " +
                    "newsletter_subscribed BOOLEAN DEFAULT FALSE)");

        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar hacer una estructura de tabla mínima.");
        }
    }
}
