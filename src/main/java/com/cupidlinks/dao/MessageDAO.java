package com.cupidlinks.dao;

import com.cupidlinks.model.Message;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for matched-user conversations.
 */
public class MessageDAO {

    /**
     * Loads all messages for a match conversation in chronological order.
     *
     * @param matchId ID of the match conversation
     * @return list of messages in oldest-to-newest order
     * @throws SQLException if the select query fails
     */
    public List<Message> findByMatchId(int matchId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE match_id = ? ORDER BY sent_at ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) messages.add(mapRow(rs));
        }
        return messages;
    }

    /**
     * Inserts a new message into a match conversation.
     *
     * @param matchId ID of the match conversation
     * @param senderId ID of the user sending the message
     * @param content message body text
     * @return true if the message was inserted
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int matchId, int senderId, String content) throws SQLException {
        String sql = "INSERT INTO messages (match_id, sender_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            stmt.setInt(2, senderId);
            stmt.setString(3, content);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Converts the current database row into a Message model object.
     *
     * @param rs result set positioned on a message row
     * @return populated Message object
     * @throws SQLException if reading a column fails
     */
    private Message mapRow(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setMessageId(rs.getInt("message_id"));
        message.setMatchId(rs.getInt("match_id"));
        message.setSenderId(rs.getInt("sender_id"));
        message.setContent(rs.getString("content"));
        message.setSentAt(rs.getObject("sent_at", LocalDateTime.class));
        return message;
    }
}
