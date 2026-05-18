package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing a user report submitted for moderation.
 */
public class Report {

    private int reportId;
    private int reporterId;
    private int reportedUserId;
    private String reason;
    private String status;
    private LocalDateTime reportedAt;

    /**
     * Creates an empty Report object.
     */
    public Report() {}

    /**
     * @return unique report ID
     */
    public int getReportId() { return reportId; }

    /**
     * @param reportId unique report ID
     */
    public void setReportId(int reportId) { this.reportId = reportId; }

    /**
     * @return ID of the user who submitted the report
     */
    public int getReporterId() { return reporterId; }

    /**
     * @param reporterId ID of the user who submitted the report
     */
    public void setReporterId(int reporterId) { this.reporterId = reporterId; }

    /**
     * @return ID of the user being reported
     */
    public int getReportedUserId() { return reportedUserId; }

    /**
     * @param reportedUserId ID of the user being reported
     */
    public void setReportedUserId(int reportedUserId) { this.reportedUserId = reportedUserId; }

    /**
     * @return reason submitted with the report
     */
    public String getReason() { return reason; }

    /**
     * @param reason reason submitted with the report
     */
    public void setReason(String reason) { this.reason = reason; }

    /**
     * @return current report moderation status
     */
    public String getStatus() { return status; }

    /**
     * @param status current report moderation status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * @return date and time when the report was submitted
     */
    public LocalDateTime getReportedAt() { return reportedAt; }

    /**
     * @param reportedAt date and time when the report was submitted
     */
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }
}
