package com.cupidlinks.model;

import java.time.LocalDateTime;

public class Report {

    private int reportId;
    private int reporterId;
    private int reportedUserId;
    private String reason;
    private String status;
    private LocalDateTime reportedAt;

    public Report() {}

    public int getReportId() { return reportId; }
    public void setReportId(int reportId) { this.reportId = reportId; }

    public int getReporterId() { return reporterId; }
    public void setReporterId(int reporterId) { this.reporterId = reporterId; }

    public int getReportedUserId() { return reportedUserId; }
    public void setReportedUserId(int reportedUserId) { this.reportedUserId = reportedUserId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }
}