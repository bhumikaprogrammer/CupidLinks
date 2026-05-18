package com.cupidlinks.dao;

import com.cupidlinks.model.Match;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for the matches table.
 * It creates user matches and retrieves match records for profile and admin views.
 */
public class MatchDAO {

    /**
     * Creates a match between two users.
     * User IDs are stored in a consistent smaller-to-larger order to avoid duplicates.
     *
     * @param user1Id ID of the first matched user
     * @param user2Id ID of the second matched user
     * @return true if the match was inserted successfully
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int user1Id, int user2Id) throws SQLException {
        int smaller = Math.min(user1Id, user2Id);
        int larger  = Math.max(user1Id, user2Id);
        String sql = "INSERT IGNORE INTO matches (user1_id, user2_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, smaller);
            stmt.setInt(2, larger);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Loads all matches involving a specific user.
     *
     * @param userId ID of the user whose matches are being loaded
     * @return list of Match objects ordered by newest first
     * @throws SQLException if the select query fails
     */
    public List<Match> findByUserId(int userId) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM matches WHERE user1_id = ? OR user2_id = ? ORDER BY matched_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Match m = new Match();
                m.setMatchId(rs.getInt("match_id"));
                m.setUser1Id(rs.getInt("user1_id"));
                m.setUser2Id(rs.getInt("user2_id"));
                matches.add(m);
            }
        }
        return matches;
    }

    public Match findByIdForUser(int matchId, int userId) throws SQLException {
        String sql = "SELECT * FROM matches WHERE match_id = ? AND (user1_id = ? OR user2_id = ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Match m = new Match();
                m.setMatchId(rs.getInt("match_id"));
                m.setUser1Id(rs.getInt("user1_id"));
                m.setUser2Id(rs.getInt("user2_id"));
                return m;
            }
        }
        return null;
    }

    /**
     * Counts all match records in the system.
     *
     * @return total number of matches
     * @throws SQLException if the count query fails
     */
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM matches";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<java.util.Map<String, Object>> countByMonth() throws SQLException {
        List<java.util.Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(matched_at, '%Y-%m') AS month_label, COUNT(*) AS total FROM matches GROUP BY month_label ORDER BY month_label";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                java.util.Map<String, Object> row = new java.util.HashMap<>();
                row.put("month", rs.getString("month_label"));
                row.put("total", rs.getInt("total"));
                rows.add(row);
            }
        }
        return rows;
    }
}
