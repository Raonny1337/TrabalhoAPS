package core.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "biblioteca$$123";
}
