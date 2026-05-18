package com.cupidlinks.controller;

import com.cupidlinks.model.Report;
import com.cupidlinks.service.ReportService;
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
 * Controller for the admin report management page.
 */
@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

    /**
     * Loads all user reports for admin review.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response used to forward to the reports JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Report> reports = reportService.getAllReports();
            request.setAttribute("reports", reports);
            request.getRequestDispatcher("/WEB-INF/views/admin/reports.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load reports.");
            request.getRequestDispatcher("/WEB-INF/views/admin/reports.jsp").forward(request, response);
        }
    }

    /**
     * Updates a report's moderation status from the admin page.
     *
     * @param request HTTP request containing reportId and status values
     * @param response HTTP response used to redirect back to the reports page
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportIdStr = ValidationUtil.sanitize(request.getParameter("reportId"));
        String status      = ValidationUtil.sanitize(request.getParameter("status"));

        try {
            int reportId = Integer.parseInt(reportIdStr);
            boolean updated;
            if ("resolved".equals(status)) {
                updated = reportService.resolveReportWithSuspension(reportId);
            } else {
                updated = reportService.updateReportStatus(reportId, status);
            }

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/admin/reports?success=" + status);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/reports?error=true");
            }
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reports?error=true");
        }
    }
}
