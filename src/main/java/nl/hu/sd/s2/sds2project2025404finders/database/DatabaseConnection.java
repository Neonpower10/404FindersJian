package nl.hu.sd.s2.sds2project2025404finders.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    /**
     * Returns a database connection.
     * If no connection exists, creates a new connection and returns it.
     * If a connection already exists, returns that connection.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName("org.postgresql.Driver");

            String url =
                    "jdbc:postgresql://localhost:5432/404FindersJian?user=postgres&password=postgres";

            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    /**
     * If a connection is still open, closes that connection.
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
}
