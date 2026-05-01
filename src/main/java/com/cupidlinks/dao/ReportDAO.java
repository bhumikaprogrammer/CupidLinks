package com.cupidlinks.dao;

import com.cupidlinks.model.Report;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

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

    public boolean updateStatus(int reportId, String status) throws SQLException {
        String sql = "UPDATE reports SET status = ? WHERE report_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, reportId);
            return stmt.executeUpdate() > 0;
        }
    }
}