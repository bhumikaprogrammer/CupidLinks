package com.cupidlinks.dao;

import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for the interests table.
 * It records accepted interest actions between users and checks for mutual interest.
 */
public class InterestDAO {

    /**
     * Creates an accepted interest from one user to another.
     *
     * @param senderId ID of the user sending interest
     * @param receiverId ID of the user receiving interest
     * @return true if the interest was inserted successfully
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int senderId, int receiverId) throws SQLException {
        String sql = "INSERT IGNORE INTO interests (sender_id, receiver_id, status) VALUES (?, ?, 'accepted')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Checks whether the second user has already accepted interest in the first user.
     *
     * @param user1 ID of the first user
     * @param user2 ID of the second user
     * @return true if a matching accepted interest exists in the opposite direction
     * @throws SQLException if the select query fails
     */
    public boolean mutualExists(int user1, int user2) throws SQLException {
        String sql = "SELECT COUNT(*) FROM interests WHERE sender_id = ? AND receiver_id = ? AND status = 'accepted'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user2);
            stmt.setInt(2, user1);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }

    public List<String> findTagsByUserId(int userId) throws SQLException {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT interest_tag FROM user_interests WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) tags.add(rs.getString("interest_tag"));
        }
        return tags;
    }

    /**
     * Replaces a user's profile interest tags with the submitted set.
     *
     * @param userId ID of the user whose tags are being updated
     * @param tags selected interest tags
     * @throws SQLException if deleting or inserting tags fails
     */
    public void replaceTags(int userId, List<String> tags) throws SQLException {
        String deleteSql = "DELETE FROM user_interests WHERE user_id = ?";
        String insertSql = "INSERT IGNORE INTO user_interests (user_id, interest_tag) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                deleteStmt.setInt(1, userId);
                deleteStmt.executeUpdate();

                for (String tag : tags) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, tag);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
