package nl.hu.sd.s2.sds2project2025404finders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://145.89.192.235:5432/fof_camping_project";
    private static final String USER = "postgres";
    private static final String PASSWORD = "404finders";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
