package com.cupidlinks.service;

import com.cupidlinks.dao.ReportDAO;
import com.cupidlinks.dao.UserDAO;
import com.cupidlinks.model.Report;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for report submission and admin report review.
 */
public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();
    private final UserDAO userDAO = new UserDAO();

    /**
     * Loads all submitted reports.
     *
     * @return list of Report objects
     * @throws SQLException if the database query fails
     */
    public List<Report> getAllReports() throws SQLException {
        return reportDAO.findAll();
    }

    /**
     * Updates the moderation status of a report.
     *
     * @param reportId ID of the report being updated
     * @param status new moderation status
     * @return true if the report was updated
     * @throws SQLException if the database update fails
     */
    public boolean updateReportStatus(int reportId, String status) throws SQLException {
        if (!"reviewed".equals(status) && !"dismissed".equals(status)) {
            return false;
        }
        return reportDAO.updateStatus(reportId, status);
    }

    /**
     * Resolves a report by taking moderation action against the reported user.
     * In this project, resolving a valid report suspends that account.
     *
     * @param reportId ID of the report being resolved
     * @return true if the user was suspended and the report was marked resolved
     * @throws SQLException if either database update fails
     */
    public boolean resolveReportWithSuspension(int reportId) throws SQLException {
        Report report = reportDAO.findById(reportId);
        if (report == null) {
            return false;
        }

        boolean userSuspended = userDAO.updateStatus(report.getReportedUserId(), "suspended");
        boolean reportResolved = reportDAO.updateStatus(reportId, "resolved");
        return userSuspended && reportResolved;
    }

    /**
     * Submits a new report against another user.
     *
     * @param reporterId ID of the user submitting the report
     * @param reportedUserId ID of the user being reported
     * @param reason explanation submitted with the report
     * @return true if the report was saved
     * @throws SQLException if the database insert fails
     */
    public boolean submitReport(int reporterId, int reportedUserId, String reason) throws SQLException {
        return reportDAO.insert(reporterId, reportedUserId, reason);
    }
}
