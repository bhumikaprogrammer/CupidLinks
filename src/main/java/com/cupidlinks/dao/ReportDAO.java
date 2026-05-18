package com.cupidlinks.dao;

import com.cupidlinks.model.Report;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for the reports table.
 * It stores user reports and supports admin review actions.
 */
public class ReportDAO {

    /**
     * Loads every report for the admin report page.
     *
     * @return list of Report objects ordered by newest first
     * @throws SQLException if the select query fails
     */
    public List<Report> findAll() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports ORDER BY reported_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Report r = new Report();
                r.setReportId(rs.getInt("report_id"));
                r.setReporterId(rs.getInt("reporter_id"));
                r.setReportedUserId(rs.getInt("reported_user_id"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getString("status"));
                reports.add(r);
            }
        }
        return reports;
    }

    /**
     * Loads one report by ID so admin actions can use its reported user.
     *
     * @param reportId ID of the report to load
     * @return matching Report, or null when no report exists
     * @throws SQLException if the select query fails
     */
    public Report findById(int reportId) throws SQLException {
        String sql = "SELECT * FROM reports WHERE report_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reportId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Report r = new Report();
                    r.setReportId(rs.getInt("report_id"));
                    r.setReporterId(rs.getInt("reporter_id"));
                    r.setReportedUserId(rs.getInt("reported_user_id"));
                    r.setReason(rs.getString("reason"));
                    r.setStatus(rs.getString("status"));
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * Inserts a new report submitted by one user against another user.
     *
     * @param reporterId ID of the user creating the report
     * @param reportedUserId ID of the user being reported
     * @param reason explanation submitted with the report
     * @return true if the report was inserted successfully
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int reporterId, int reportedUserId, String reason) throws SQLException {
        String sql = "INSERT INTO reports (reporter_id, reported_user_id, reason) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reporterId);
            stmt.setInt(2, reportedUserId);
            stmt.setString(3, reason);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates the moderation status of an existing report.
     *
     * @param reportId ID of the report being updated
     * @param status new report status
     * @return true if the report status was updated
     * @throws SQLException if the update query fails
     */
    public boolean updateStatus(int reportId, String status) throws SQLException {
        String sql = "UPDATE reports SET status = ? WHERE report_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, reportId);
            return stmt.executeUpdate() > 0;
        }
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM reports";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}
