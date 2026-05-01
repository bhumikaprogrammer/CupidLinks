<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/12/2026
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reports - CupidLinks Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-wrapper">
    <aside class="sidebar">
        <div class="sidebar-brand">
            <div class="heart-small"></div>
            <span>CupidLinks</span>
        </div>
        <nav class="sidebar-nav">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item active">Reports</a>
            <a href="${pageContext.request.contextPath}/admin/support" class="nav-item">Support</a>
            <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">Logout</a>
        </nav>
    </aside>

    <main class="admin-main">
        <div class="admin-header">
            <h1>User Reports</h1>
            <span>Welcome, <strong>${sessionScope.email}</strong></span>
        </div>

        <c:if test="${param.success eq 'true'}">
            <div class="alert alert-success">Report updated successfully.</div>
        </c:if>
        <c:if test="${param.error eq 'true'}">
            <div class="alert alert-error">Something went wrong. Please try again.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <div class="table-section">
            <div class="table-toolbar">
                <h2>All Reports</h2>
            </div>
            <div class="table-wrap">
                <table class="admin-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Reporter ID</th>
                        <th>Reported User ID</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="report" items="${reports}">
                        <tr>
                            <td>${report.reportId}</td>
                            <td>${report.reporterId}</td>
                            <td>${report.reportedUserId}</td>
                            <td>${report.reason}</td>
                            <td><span class="badge badge-${report.status}">${report.status}</span></td>
                            <td class="action-btns">
                                <c:if test="${report.status eq 'open'}">
                                    <form action="${pageContext.request.contextPath}/admin/reports" method="post" style="display:inline">
                                        <input type="hidden" name="reportId" value="${report.reportId}">
                                        <input type="hidden" name="status" value="reviewed">
                                        <button type="submit" class="btn btn-warning">Mark Reviewed</button>
                                    </form>
                                </c:if>
                                <c:if test="${report.status ne 'resolved'}">
                                    <form action="${pageContext.request.contextPath}/admin/reports" method="post" style="display:inline">
                                        <input type="hidden" name="reportId" value="${report.reportId}">
                                        <input type="hidden" name="status" value="resolved">
                                        <button type="submit" class="btn btn-success">Resolve</button>
                                    </form>
                                </c:if>
                                <c:if test="${report.status ne 'dismissed'}">
                                    <form action="${pageContext.request.contextPath}/admin/reports" method="post" style="display:inline">
                                        <input type="hidden" name="reportId" value="${report.reportId}">
                                        <input type="hidden" name="status" value="dismissed">
                                        <button type="submit" class="btn btn-danger">Dismiss</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty reports}">
                        <tr>
                            <td colspan="6" style="text-align:center;padding:30px;color:#888;">No reports found.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>

