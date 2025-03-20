// SID: 2408078
package com.trackgenesis.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Class that deals with password hashing
 * @author henryburbridge
 */
public class Hashing {

    /**
     * Hashes a given password using the BCrypt algorithm.
     * @param password users password
     * @return The BCrypt hash of the password as a String.
     */
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks password against hashed password
     * @param userEnteredPassword the users password
     * @param storedHashedPassword the stored hash of the password
     * @return boolean value if they match
     */
    public boolean checkHash(String userEnteredPassword, String storedHashedPassword) {
        return BCrypt.checkpw(userEnteredPassword, storedHashedPassword);
    }

}