package com.cupidlinks.dao;

import com.cupidlinks.model.Match;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

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

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM matches";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}