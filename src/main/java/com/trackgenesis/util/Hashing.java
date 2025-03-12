package com.trackgenesis.util;

import org.mindrot.jbcrypt.BCrypt;

public class Hashing {

    // Hashes password
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Checks hashed password
    // Takes the unhashed password and the hashed version and compares it
    public boolean checkHash(String userEnteredPassword, String storedHashedPassword) {
        return BCrypt.checkpw(userEnteredPassword, storedHashedPassword);
    }

}