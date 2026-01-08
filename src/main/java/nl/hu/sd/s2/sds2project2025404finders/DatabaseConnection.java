package nl.hu.sd.s2.sds2project2025404finders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    /**
     Returns a database connection. If no connection exists, creates a new connection and returns it, if a connection does exist, returns that connection. Loading the driver using Class.forName(..) is necessary before you can use it.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");

            String url =
                    "jdbc:postgresql://145.89.192.235:5432/fof_camping_project?user=postgres&password=404finders";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    /**
     If a connection is still open (connection != null), closes that connection, otherwise does nothing
     */
    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
