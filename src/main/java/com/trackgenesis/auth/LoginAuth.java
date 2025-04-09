// SID: 2408078
package com.trackgenesis.auth;

import com.trackgenesis.util.Hashing;

import java.sql.*;

/**
 * Class containing the logic for a user to login
 * @author henryburbridge
 */
public class LoginAuth {
    private final Hashing hash;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    /**
     * Constructor
     * Calls method to load users from the file into HashMap
     */
    public LoginAuth() {
        this.hash = new Hashing();
        this.dbUrl = System.getenv("DB_URL");
        this.dbUsername = System.getenv("DB_USERNAME");
        this.dbPassword = System.getenv("DB_PASSWORD");



    }

    /**
     * Get the user's hashed password from the database
     * @param username the inputed username
     * @return the hashed password
     */
    private String getHashedPassword(String username) {
        String sql = "SELECT `Password` FROM users WHERE `Username` = ?";
        String hashedPassword = null; // Initialize to null

        // Connect to database
        try (Connection connection = DriverManager.getConnection(this.dbUrl, this.dbUsername, this.dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Insert username var into prepared statement
            preparedStatement.setString(1, username);

            // Get the result from the SQL query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Store in var
                    hashedPassword = resultSet.getString("Password"); // Retrieve hashed password
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        return hashedPassword; // Return the hashed password or null
    }

    /**
     * Main logic to login
     * @param username the user's username
     * @param password the user's password
     * @return True if logged in, false if not
     */
    public boolean login(String username, String password) {
        String storedHashedPassword = this.getHashedPassword(username); // Retrieve the stored hashed password

        if (storedHashedPassword != null) {
            return this.hash.checkHash(password, storedHashedPassword); // Check the password
        } else {
            return false; // User not found
        }
    }
}