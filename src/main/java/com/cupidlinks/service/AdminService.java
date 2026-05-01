package com.cupidlinks.service;

import com.cupidlinks.dao.MatchDAO;
import com.cupidlinks.dao.UserDAO;
import com.cupidlinks.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for admin dashboard features.
 * It gathers user-management and analytics data needed by the admin pages.
 */
public class AdminService {

    private final UserDAO  userDAO  = new UserDAO();
    private final MatchDAO matchDAO = new MatchDAO();

    /**
     * Loads all normal users for dashboard display.
     *
     * @param sort selected sort option
     * @return list of users
     * @throws SQLException if the query fails
     */
    public List<User> getAllUsers(String sort) throws SQLException {
        return userDAO.getAllUsers(sort);
    }

    /**
     * Searches users by email or phone.
     *
     * @param keyword search text entered by admin
     * @param sort selected sort option
     * @return list of matching users
     * @throws SQLException if the query fails
     */
    public List<User> searchUsers(String keyword, String sort) throws SQLException {
        return userDAO.searchUsers(keyword, sort);
    }

    /**
     * Changes user status, such as approved or suspended.
     *
     * @param userId user account ID
     * @param status new status value
     * @return true if the status was updated
     * @throws SQLException if the update fails
     */
    public boolean updateUserStatus(int userId, String status) throws SQLException {
        return userDAO.updateStatus(userId, status);
    }

    /**
     * Deletes a user account from admin management.
     *
     * @param userId user account ID
     * @return true if the user was deleted
     * @throws SQLException if the delete fails
     */
    public boolean deleteUser(int userId) throws SQLException {
        return userDAO.deleteUser(userId);
    }

    /**
     * Counts all normal users for dashboard analytics.
     *
     * @return total number of users
     * @throws SQLException if the count query fails
     */
    public int getTotalUsers() throws SQLException {
        return userDAO.countByStatus(null);
    }

    /**
     * Counts active approved users.
     *
     * @return number of approved users
     * @throws SQLException if the count query fails
     */
    public int getApprovedUsers() throws SQLException {
        return userDAO.countByStatus("approved");
    }

    /**
     * Counts suspended users so the admin can monitor moderation.
     *
     * @return number of suspended users
     * @throws SQLException if the count query fails
     */
    public int getSuspendedUsers() throws SQLException {
        return userDAO.countByStatus("suspended");
    }

    /**
     * Counts total matches created from mutual interests.
     *
     * @return total number of matches
     * @throws SQLException if the count query fails
     */
    public int getTotalMatches() throws SQLException {
        return matchDAO.countAll();
    }
}
