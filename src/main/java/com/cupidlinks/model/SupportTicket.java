package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing a support ticket submitted by a user.
 */
public class SupportTicket {

    private int ticketId;
    private int userId;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime submittedAt;

    /**
     * Creates an empty SupportTicket object.
     */
    public SupportTicket() {}

    /**
     * @return unique support ticket ID
     */
    public int getTicketId() { return ticketId; }

    /**
     * @param ticketId unique support ticket ID
     */
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    /**
     * @return ID of the user who submitted the ticket
     */
    public int getUserId() { return userId; }

    /**
     * @param userId ID of the user who submitted the ticket
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * @return ticket subject
     */
    public String getSubject() { return subject; }

    /**
     * @param subject ticket subject
     */
    public void setSubject(String subject) { this.subject = subject; }

    /**
     * @return support request message
     */
    public String getMessage() { return message; }

    /**
     * @param message support request message
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * @return current ticket status
     */
    public String getStatus() { return status; }

    /**
     * @param status current ticket status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * @return date and time when the ticket was submitted
     */
    public LocalDateTime getSubmittedAt() { return submittedAt; }

    /**
     * @param submittedAt date and time when the ticket was submitted
     */
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
