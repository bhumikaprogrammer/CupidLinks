package com.cupidlinks.dao;

import com.cupidlinks.model.SupportTicket;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for support ticket records.
 */
public class SupportDAO {

    /**
     * Inserts a support ticket submitted from the contact page.
     *
     * @param userId ID of the user submitting the ticket
     * @param subject ticket subject
     * @param message ticket message body
     * @return true if the ticket was inserted
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int userId, String subject, String message) throws SQLException {
        String sql = "INSERT INTO support_tickets (user_id, subject, message) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, subject);
            stmt.setString(3, message);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Loads all support tickets for admin review.
     *
     * @return list of support tickets ordered by newest first
     * @throws SQLException if the select query fails
     */
    public List<SupportTicket> findAll() throws SQLException {
        List<SupportTicket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM support_tickets ORDER BY submitted_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SupportTicket ticket = new SupportTicket();
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setUserId(rs.getInt("user_id"));
                ticket.setSubject(rs.getString("subject"));
                ticket.setMessage(rs.getString("message"));
                ticket.setStatus(rs.getString("status"));
                ticket.setSubmittedAt(rs.getObject("submitted_at", LocalDateTime.class));
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    /**
     * Updates the current status of a support ticket.
     *
     * @param ticketId ID of the support ticket
     * @param status new ticket status
     * @return true if the status was updated
     * @throws SQLException if the update query fails
     */
    public boolean updateStatus(int ticketId, String status) throws SQLException {
        String sql = "UPDATE support_tickets SET status = ? WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, ticketId);
            return stmt.executeUpdate() > 0;
        }
    }
}
