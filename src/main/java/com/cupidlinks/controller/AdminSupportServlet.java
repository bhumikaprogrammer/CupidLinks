package com.cupidlinks.controller;

import com.cupidlinks.model.SupportTicket;
import com.cupidlinks.service.SupportService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for admin support ticket management.
 */
@WebServlet("/admin/support")
public class AdminSupportServlet extends HttpServlet {

    private final SupportService supportService = new SupportService();

    /**
     * Loads all support tickets for the admin support page.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response used to forward to the support JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<SupportTicket> tickets = supportService.getAllTickets();
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("/WEB-INF/views/admin/support.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Could not load support tickets.");
            request.getRequestDispatcher("/WEB-INF/views/admin/support.jsp").forward(request, response);
        }
    }

    /**
     * Updates the status of a support ticket.
     *
     * @param request HTTP request containing ticketId and status values
     * @param response HTTP response used to redirect back to the support page
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ticketId = ValidationUtil.sanitize(request.getParameter("ticketId"));
        String status   = ValidationUtil.sanitize(request.getParameter("status"));
        try {
            supportService.updateTicketStatus(Integer.parseInt(ticketId), status);
            response.sendRedirect(request.getContextPath() + "/admin/support?success=true");
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/support?error=true");
        }
    }
}
