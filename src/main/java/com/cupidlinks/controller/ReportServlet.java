package com.cupidlinks.controller;

import com.cupidlinks.service.ReportService;
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
 * Controller for submitting reports against other users.
 */
@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

    /**
     * Validates and saves a report submitted by the logged-in user.
     *
     * @param request HTTP request containing reportedUserId and reason values
     * @param response HTTP response used to redirect after submission
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int reporterId = (int) session.getAttribute("userId");

        String reportedUserIdStr = ValidationUtil.sanitize(request.getParameter("reportedUserId"));
        String reason            = ValidationUtil.sanitize(request.getParameter("reason"));

        if (ValidationUtil.isEmpty(reason) || ValidationUtil.isEmpty(reportedUserIdStr)) {
            response.sendRedirect(request.getContextPath() + "/discover?error=true");
            return;
        }

        try {
            int reportedUserId = Integer.parseInt(reportedUserIdStr);
            reportService.submitReport(reporterId, reportedUserId, reason);
            response.sendRedirect(request.getContextPath() + "/discover?reported=true");
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/discover?error=true");
        }
    }
}
