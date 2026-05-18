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
        String source            = ValidationUtil.sanitize(request.getParameter("source"));

        if (ValidationUtil.isEmpty(reason) || ValidationUtil.isEmpty(reportedUserIdStr)) {
            response.sendRedirect(buildRedirectUrl(request, source, false));
            return;
        }

        try {
            int reportedUserId = Integer.parseInt(reportedUserIdStr);
            reportService.submitReport(reporterId, reportedUserId, reason);
            response.sendRedirect(buildRedirectUrl(request, source, true));
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(buildRedirectUrl(request, source, false));
        }
    }

    /**
     * Returns the user to the page where the report was submitted.
     *
     * @param request current HTTP request
     * @param source source page submitted by the report form
     * @param success true when the report was saved
     * @return safe application-local redirect URL
     */
    private String buildRedirectUrl(HttpServletRequest request, String source, boolean success) {
        String status = success ? "reported=true" : "error=true";
        if ("matches".equals(source)) {
            return request.getContextPath() + "/matches?" + status;
        }
        return request.getContextPath() + "/discover?" + status;
    }
}
