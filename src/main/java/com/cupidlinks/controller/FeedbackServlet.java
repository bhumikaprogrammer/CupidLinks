package com.cupidlinks.controller;

import com.cupidlinks.service.FeedbackService;
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
 * Controller for user feedback submissions.
 */
@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    private final FeedbackService feedbackService = new FeedbackService();

    /**
     * Displays the feedback form for a logged-in user.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response used to forward to the feedback JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/feedback.jsp").forward(request, response);
    }

    /**
     * Validates and saves a user's star rating and comment.
     *
     * @param request HTTP request containing rating and comment fields
     * @param response HTTP response used to redirect after saving
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String ratingParam = ValidationUtil.sanitize(request.getParameter("rating"));
        String comment = ValidationUtil.sanitize(request.getParameter("comment"));

        try {
            int rating = Integer.parseInt(ratingParam);
            boolean saved = feedbackService.submitFeedback(userId, rating, comment);
            response.sendRedirect(request.getContextPath() + "/feedback?" + (saved ? "success=true" : "error=true"));
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/feedback?error=true");
        }
    }
}
