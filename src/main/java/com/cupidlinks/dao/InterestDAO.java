package com.cupidlinks.dao;

import com.cupidlinks.util.DBConnection;

import java.sql.*;

public class InterestDAO {

    public boolean insert(int senderId, int receiverId) throws SQLException {
        String sql = "INSERT IGNORE INTO interests (sender_id, receiver_id, status) VALUES (?, ?, 'accepted')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            return stmt.executeUpdate() > 0;
        }
    }

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
}