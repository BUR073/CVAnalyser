// SID: 2408078
package com.trackgenesis.auth;
import com.trackgenesis.util.Hashing;

import java.sql.*;


/**
 * Class containing the logic for registering a new user
 * @author henryburbridge
 */
public class RegisterAuth {


    private final Hashing hash;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;



    /**
     * Constructor
     * Defines a new Hashing object
     */
    public RegisterAuth() {
        this.hash = new Hashing();
        this.dbUrl = System.getenv("DB_URL");
        this.dbUsername = System.getenv("DB_USERNAME");
        this.dbPassword = System.getenv("DB_PASSWORD");
    }

    /**
     * Main logic for registering a user
     * @param username new username
     * @param password new password
     * @return true if successfully register, false otherwise
     */

    public boolean register(String username, String password) {
        // Check if the username already exists
        try {
            if (usernameExist(username)) {
                System.out.println("Username " + username + " already exists.");
                return false; // Registration failed
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        password = hash.hash(password);
        String sql = "INSERT INTO `Users` (`username`, `password`) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate(); // Execute the INSERT statement
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
            return true; // Registration successful
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Checks whether the username already exists
     * @param username new username
     * @return true if the username already exists, false if not
     */
    private boolean usernameExist(String username) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE username = ? LIMIT 1;";

        try (Connection connection = DriverManager.getConnection(this.dbUrl, this.dbUsername, this.dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a row exists, false otherwise
            }
        }

    }

}