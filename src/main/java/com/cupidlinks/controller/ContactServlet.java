package com.cupidlinks.controller;

import com.cupidlinks.service.SupportService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for the contact and support request page.
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    private final SupportService supportService = new SupportService();

    /**
     * Displays the contact form.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response used to forward to the contact JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/shared/contact.jsp").forward(request, response);
    }

    /**
     * Validates and submits a support ticket from the contact form.
     *
     * @param request HTTP request containing subject and message values
     * @param response HTTP response used to forward or redirect after submission
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if redirecting or writing the response fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String subject = ValidationUtil.sanitize(request.getParameter("subject"));
        String message = ValidationUtil.sanitize(request.getParameter("message"));

        if (ValidationUtil.isEmpty(subject) || ValidationUtil.isEmpty(message)) {
            request.setAttribute("error", "Subject and message are required.");
            request.getRequestDispatcher("/WEB-INF/views/shared/contact.jsp").forward(request, response);
            return;
        }

        try {
            supportService.submitTicket(userId, subject, message);
            response.sendRedirect(request.getContextPath() + "/contact?sent=true");
        } catch (SQLException e) {
            request.setAttribute("error", "Could not send your message. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/shared/contact.jsp").forward(request, response);
        }
    }
}
