package com.cupidlinks.dao;

import com.cupidlinks.model.User;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for the users table.
 * All SQL related to user accounts is kept here so controllers and services
 * do not need to know the database query details.
 */
public class UserDAO {

    /**
     * Checks whether an email is already used by another account.
     *
     * @param email email address to check
     * @return true if a matching user record exists
     * @throws SQLException if the database query fails
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    /**
     * Checks whether a phone number is already used by another account.
     *
     * @param phone phone number to check
     * @return true if a matching user record exists
     * @throws SQLException if the database query fails
     */
    public boolean phoneExists(String phone) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    /**
     * Inserts a new normal user account.
     *
     * @param email user's unique email address
     * @param phone user's unique phone number
     * @param passwordHash BCrypt hashed password
     * @return true if the user was inserted successfully
     * @throws SQLException if the insert query fails
     */
    public boolean insertUser(String email, String phone, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (email, phone, password_hash, role, status) VALUES (?, ?, ?, 'user', 'approved')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, phone);
            stmt.setString(3, passwordHash);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Finds a user account by email during login.
     *
     * @param email email address entered by the user
     * @return matching User object, or null when no account is found
     * @throws SQLException if the select query fails
     */
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    /**
     * Loads all normal users for the admin dashboard.
     *
     * @param sort selected sort option from the dashboard filter
     * @return list of users ordered according to the selected option
     * @throws SQLException if the select query fails
     */
    public List<User> getAllUsers(String sort) throws SQLException {
        String orderBy = "created_at DESC";
        if ("name_asc".equals(sort))  orderBy = "email ASC";
        if ("name_desc".equals(sort)) orderBy = "email DESC";
        if ("status".equals(sort))    orderBy = "status ASC";

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'user' ORDER BY " + orderBy;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) users.add(mapRow(rs));
        }
        return users;
    }

    /**
     * Searches users by email or phone for admin management.
     *
     * @param keyword text entered in the dashboard search box
     * @param sort selected sort option
     * @return list of matching users
     * @throws SQLException if the search query fails
     */
    public List<User> searchUsers(String keyword, String sort) throws SQLException {
        String orderBy = "created_at DESC";
        if ("name_asc".equals(sort))  orderBy = "email ASC";
        if ("name_desc".equals(sort)) orderBy = "email DESC";
        if ("status".equals(sort))    orderBy = "status ASC";

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'user' AND (email LIKE ? OR phone LIKE ?) ORDER BY " + orderBy;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) users.add(mapRow(rs));
        }
        return users;
    }

    /**
     * Updates the account status, for example approved or suspended.
     *
     * @param userId ID of the user being updated
     * @param status new account status
     * @return true if the status was updated
     * @throws SQLException if the update query fails
     */
    public boolean updateStatus(int userId, String status) throws SQLException {
        String sql = "UPDATE users SET status = ? WHERE user_id = ? AND role = 'user'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a normal user account from the system.
     *
     * @param userId ID of the user being deleted
     * @return true if the user was deleted
     * @throws SQLException if the delete query fails
     */
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'user'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Counts users by status for dashboard analytics.
     *
     * @param status account status to count, or null to count all normal users
     * @return number of matching users
     * @throws SQLException if the count query fails
     */
    public int countByStatus(String status) throws SQLException {
        String sql = status == null
                ? "SELECT COUNT(*) FROM users WHERE role = 'user'"
                : "SELECT COUNT(*) FROM users WHERE role = 'user' AND status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (status != null) stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    /**
     * Converts the current database row into a User model object.
     *
     * @param rs result set positioned on a user row
     * @return populated User object
     * @throws SQLException if reading a column fails
     */
    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}
