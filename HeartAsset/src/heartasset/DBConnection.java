package heartasset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/assetdb";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DBConnection() { }

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) { // Check if connection is not open
            try {
                // Establish a new connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Unable to establish a connection to the database. Check the URL, user credentials, or database server.", e);
            }
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close(); // Close the connection
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection: " + e.getMessage());
            }
        }
    }
}
