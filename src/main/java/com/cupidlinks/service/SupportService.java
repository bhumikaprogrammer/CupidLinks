package com.cupidlinks.service;

import com.cupidlinks.util.DBConnection;

import java.sql.*;

public class SupportService {

    public boolean submitTicket(int userId, String subject, String message) throws SQLException {
        String sql = "INSERT INTO support_tickets (user_id, subject, message) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, subject);
            stmt.setString(3, message);
            return stmt.executeUpdate() > 0;
        }
    }
}