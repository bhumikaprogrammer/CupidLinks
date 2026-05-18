package com.cupidlinks.controller;

import com.cupidlinks.service.AdminService;
import com.cupidlinks.service.FeedbackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for admin analytics summaries.
 */
@WebServlet("/admin/analytics")
public class AdminAnalyticsServlet extends HttpServlet {

    private final AdminService adminService = new AdminService();
    private final FeedbackService feedbackService = new FeedbackService();

    /**
     * Loads summary totals and monthly analytics for the admin analytics page.
     *
     * @param request HTTP request from the admin browser
     * @param response HTTP response used to forward to the analytics JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("totalUsers", adminService.getTotalUsers());
            request.setAttribute("totalMatches", adminService.getTotalMatches());
            request.setAttribute("totalReports", adminService.getTotalReports());
            request.setAttribute("totalFeedback", feedbackService.getTotalFeedback());
            request.setAttribute("averageRating", String.format("%.1f", feedbackService.getAverageRating()));
            request.setAttribute("recentFeedback", feedbackService.getRecentFeedback(5));
            request.setAttribute("userMonths", adminService.getUserRegistrationsByMonth());
            request.setAttribute("matchMonths", adminService.getMatchesByMonth());
            request.getRequestDispatcher("/WEB-INF/views/admin/analytics.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load analytics.");
            request.getRequestDispatcher("/WEB-INF/views/admin/analytics.jsp").forward(request, response);
        }
    }
}
