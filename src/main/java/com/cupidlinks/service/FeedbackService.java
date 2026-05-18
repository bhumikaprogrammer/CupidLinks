package com.cupidlinks.service;

import com.cupidlinks.dao.FeedbackDAO;
import com.cupidlinks.model.Feedback;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for user feedback.
 */
public class FeedbackService {

    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    /**
     * Validates and submits feedback for a user.
     *
     * @param userId ID of the user submitting feedback
     * @param rating star rating from 1 to 5
     * @param comment optional feedback comment
     * @return true if the feedback was saved
     * @throws SQLException if the database insert fails
     */
    public boolean submitFeedback(int userId, int rating, String comment) throws SQLException {
        if (rating < 1 || rating > 5) {
            return false;
        }
        return feedbackDAO.insert(userId, rating, comment);
    }

    public int getTotalFeedback() throws SQLException {
        return feedbackDAO.countAll();
    }

    public double getAverageRating() throws SQLException {
        return feedbackDAO.getAverageRating();
    }

    public List<Feedback> getRecentFeedback(int limit) throws SQLException {
        return feedbackDAO.findRecent(limit);
    }
}
