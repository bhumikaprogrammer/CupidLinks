package com.cupidlinks.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central database connection helper for CupidLinks.
 * DAO classes use this class whenever they need to communicate with MySQL.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/cupidlinks_db?useSSL=false&serverTimezone=Asia/Kathmandu&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "9580";

    static {
        try {
            // Load the MySQL driver once when the class is first used.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    /**
     * Opens a new connection to the CupidLinks MySQL database.
     *
     * @return active SQL connection
     * @throws SQLException if the database is unavailable or credentials are wrong
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Closes a connection safely when older code needs manual closing.
     * Most DAO methods use try-with-resources, but this helper is kept for reuse.
     *
     * @param connection the SQL connection that should be closed
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Could not close connection: " + e.getMessage());
            }
        }
    }
}
