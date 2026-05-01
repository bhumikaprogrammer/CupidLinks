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

@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportIdStr = ValidationUtil.sanitize(request.getParameter("reportId"));
        String status      = ValidationUtil.sanitize(request.getParameter("status"));

        try {
            int reportId = Integer.parseInt(reportIdStr);
            reportService.updateReportStatus(reportId, status);
            response.sendRedirect(request.getContextPath() + "/admin/reports?success=true");
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reports?error=true");
        }
    }
}