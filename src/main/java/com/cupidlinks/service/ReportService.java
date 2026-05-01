package com.cupidlinks.service;

import com.cupidlinks.dao.ReportDAO;
import com.cupidlinks.model.Report;

import java.sql.SQLException;
import java.util.List;

public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();

    public List<Report> getAllReports() throws SQLException {
        return reportDAO.findAll();
    }

    public boolean updateReportStatus(int reportId, String status) throws SQLException {
        return reportDAO.updateStatus(reportId, status);
    }

    public boolean submitReport(int reporterId, int reportedUserId, String reason) throws SQLException {
        return reportDAO.insert(reporterId, reportedUserId, reason);
    }
}