package com.cupidlinks.service;

import com.cupidlinks.dao.UserDAO;
import com.cupidlinks.model.User;
import com.cupidlinks.util.PasswordUtil;

import java.sql.SQLException;

/**
 * Service layer for user account operations.
 * Controllers call this class instead of calling the DAO directly, which keeps
 * business rules such as password hashing away from the servlet code.
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Checks if the email is already registered.
     *
     * @param email email address to check
     * @return true if the email already exists
     * @throws SQLException if the database check fails
     */
    public boolean emailExists(String email) throws SQLException {
        return userDAO.emailExists(email);
    }

    /**
     * Checks if the phone number is already registered.
     *
     * @param phone phone number to check
     * @return true if the phone number already exists
     * @throws SQLException if the database check fails
     */
    public boolean phoneExists(String phone) throws SQLException {
        return userDAO.phoneExists(phone);
    }

    /**
     * Registers a new user after hashing the plain password.
     *
     * @param email user's email address
     * @param phone user's phone number
     * @param plainPassword password entered in the registration form
     * @return true if the user account was created
     * @throws SQLException if the database insert fails
     */
    public boolean registerUser(String email, String phone, String plainPassword) throws SQLException {
        // Hash here so plain passwords never reach the DAO or database.
        String hashed = PasswordUtil.hash(plainPassword);
        return userDAO.insertUser(email, phone, hashed);
    }

    /**
     * Finds a user by email for login.
     *
     * @param email email address entered during login
     * @return matching User object, or null if not found
     * @throws SQLException if the database query fails
     */
    public User findByEmail(String email) throws SQLException {
        return userDAO.findByEmail(email);
    }
}
