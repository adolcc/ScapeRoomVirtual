package repository.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            InputStream input = DatabaseConfig.class.getClassLoader()
                    .getResourceAsStream("database.properties");
            props.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("pool.minimumIdle")));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("pool.maximumPoolSize")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("pool.idleTimeout")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("pool.connectionTimeout")));

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando la configuración de la base de datos.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
