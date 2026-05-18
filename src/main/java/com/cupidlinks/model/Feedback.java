package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing feedback submitted by a user.
 */
public class Feedback {

    private int feedbackId;
    private int userId;
    private int rating;
    private String comment;
    private LocalDateTime submittedAt;

    /**
     * Creates an empty Feedback object.
     */
    public Feedback() {}

    /**
     * @return unique feedback ID
     */
    public int getFeedbackId() { return feedbackId; }

    /**
     * @param feedbackId unique feedback ID
     */
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    /**
     * @return ID of the user who submitted the feedback
     */
    public int getUserId() { return userId; }

    /**
     * @param userId ID of the user who submitted the feedback
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * @return numeric feedback rating
     */
    public int getRating() { return rating; }

    /**
     * @param rating numeric feedback rating
     */
    public void setRating(int rating) { this.rating = rating; }

    /**
     * @return feedback comment text
     */
    public String getComment() { return comment; }

    /**
     * @param comment feedback comment text
     */
    public void setComment(String comment) { this.comment = comment; }

    /**
     * @return date and time when feedback was submitted
     */
    public LocalDateTime getSubmittedAt() { return submittedAt; }

    /**
     * @param submittedAt date and time when feedback was submitted
     */
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
