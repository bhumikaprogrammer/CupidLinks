package com.cupidlinks.dao;

import com.cupidlinks.model.Feedback;
import com.cupidlinks.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for feedback submissions.
 */
public class FeedbackDAO {

    /**
     * Inserts a feedback record submitted by a user.
     *
     * @param userId ID of the user submitting feedback
     * @param rating star rating from 1 to 5
     * @param comment optional feedback comment
     * @return true if the feedback row was inserted
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int userId, int rating, String comment) throws SQLException {
        String sql = "INSERT INTO feedback (user_id, rating, comment) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, rating);
            stmt.setString(3, comment);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Counts all feedback records in the database.
     *
     * @return total number of feedback records
     * @throws SQLException if the count query fails
     */
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM feedback";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Calculates the average rating from all feedback records.
     *
     * @return average feedback rating, or 0 if no feedback exists
     * @throws SQLException if the average rating query fails
     */
    public double getAverageRating() throws SQLException {
        String sql = "SELECT COALESCE(AVG(rating), 0) FROM feedback";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    /**
     * Finds the most recently submitted feedback records.
     *
     * @param limit maximum number of feedback records to return
     * @return list of recent feedback records ordered by submission time
     * @throws SQLException if the select query fails
     */
    public List<Feedback> findRecent(int limit) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY submitted_at DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(rs.getInt("feedback_id"));
                    feedback.setUserId(rs.getInt("user_id"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setComment(rs.getString("comment"));
                    feedback.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }
}
