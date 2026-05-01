package com.cupidlinks.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Handles password hashing and password verification for the application.
 * Plain passwords should never be stored in the database, so this class uses
 * BCrypt to create one-way password hashes.
 */
public class PasswordUtil {

    private static final int BCRYPT_COST = 12;

    /**
     * Converts a plain text password into a BCrypt hash.
     *
     * @param plainPassword the password entered by the user
     * @return hashed password value that can be stored safely in the database
     */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_COST));
    }

    /**
     * Compares a login password with the stored BCrypt hash.
     *
     * @param plainPassword the password entered during login
     * @param hashedPassword the encrypted password saved in the database
     * @return true if the entered password matches the stored hash
     */
    public static boolean verify(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) return false;
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
