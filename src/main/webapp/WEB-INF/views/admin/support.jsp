<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/12/2026
  Time: 9:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Support Tickets - CupidLinks Admin</title>
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
            <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item">Reports</a>
            <a href="${pageContext.request.contextPath}/admin/support" class="nav-item active">Support</a>
            <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">Logout</a>
        </nav>
    </aside>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Support Tickets</h1>
            <span>Welcome, <strong>${sessionScope.email}</strong></span>
        </div>

        <c:if test="${param.success eq 'true'}">
            <div class="alert alert-success">Ticket updated successfully.</div>
        </c:if>
        <c:if test="${param.error eq 'true'}">
            <div class="alert alert-error">Something went wrong. Please try again.</div>
        </c:if>

        <div class="table-section">
            <div class="table-toolbar">
                <h2>All Support Tickets</h2>
            </div>
            <div class="table-wrap">
                <table class="admin-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
                        <th>Subject</th>
                        <th>Message</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ticket" items="${tickets}">
                        <tr>
                            <td>${ticket.ticketId}</td>
                            <td>${ticket.userId}</td>
                            <td>${ticket.subject}</td>
                            <td>${ticket.message}</td>
                            <td><span class="badge badge-${ticket.status}">${ticket.status}</span></td>
                            <td class="action-btns">
                                <c:if test="${ticket.status eq 'open'}">
                                    <form action="${pageContext.request.contextPath}/admin/support" method="post" style="display:inline">
                                        <input type="hidden" name="ticketId" value="${ticket.ticketId}">
                                        <input type="hidden" name="status" value="in_progress">
                                        <button type="submit" class="btn btn-warning">In Progress</button>
                                    </form>
                                </c:if>
                                <c:if test="${ticket.status ne 'resolved'}">
                                    <form action="${pageContext.request.contextPath}/admin/support" method="post" style="display:inline">
                                        <input type="hidden" name="ticketId" value="${ticket.ticketId}">
                                        <input type="hidden" name="status" value="resolved">
                                        <button type="submit" class="btn btn-success">Resolve</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty tickets}">
                        <tr>
                            <td colspan="6" style="text-align:center;padding:30px;color:#888;">No support tickets yet.</td>
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

