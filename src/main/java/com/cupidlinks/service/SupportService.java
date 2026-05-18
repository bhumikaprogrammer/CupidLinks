package com.cupidlinks.service;

import com.cupidlinks.dao.SupportDAO;
import com.cupidlinks.model.SupportTicket;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for support ticket submission.
 */
public class SupportService {

    private final SupportDAO supportDAO = new SupportDAO();

    /**
     * Saves a support ticket submitted by a user.
     *
     * @param userId ID of the user submitting the ticket
     * @param subject ticket subject
     * @param message ticket message body
     * @return true if the support ticket was inserted
     * @throws SQLException if the database insert fails
     */
    public boolean submitTicket(int userId, String subject, String message) throws SQLException {
        return supportDAO.insert(userId, subject, message);
    }

    public List<SupportTicket> getAllTickets() throws SQLException {
        return supportDAO.findAll();
    }

    public boolean updateTicketStatus(int ticketId, String status) throws SQLException {
        return supportDAO.updateStatus(ticketId, status);
    }
}
