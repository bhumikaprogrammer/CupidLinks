<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Analytics - CupidLinks Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css?v=3">
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
            <a href="${pageContext.request.contextPath}/admin/analytics" class="nav-item active">Analytics</a>
            <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item">Reports</a>
            <a href="${pageContext.request.contextPath}/admin/support" class="nav-item">Support</a>
            <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">Logout</a>
        </nav>
    </aside>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Analytics</h1>
            <span>Welcome, <strong><c:out value="${sessionScope.email}"/></strong></span>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error"><c:out value="${error}"/></div>
        </c:if>

        <div class="stats-grid">
            <div class="stat-card stat-card-accent">
                <div class="stat-number"><c:out value="${totalUsers}"/></div>
                <div class="stat-label">Registered Users</div>
            </div>
            <div class="stat-card stat-card-accent">
                <div class="stat-number"><c:out value="${totalMatches}"/></div>
                <div class="stat-label">Total Matches</div>
            </div>
            <div class="stat-card stat-card-accent">
                <div class="stat-number"><c:out value="${totalReports}"/></div>
                <div class="stat-label">Total Reports</div>
            </div>
            <div class="stat-card stat-card-accent">
                <div class="stat-number"><c:out value="${averageRating}"/>/5</div>
                <div class="stat-label">Average Rating</div>
            </div>
            <div class="stat-card stat-card-accent">
                <div class="stat-number"><c:out value="${totalFeedback}"/></div>
                <div class="stat-label">Feedback Received</div>
            </div>
        </div>

        <div class="analytics-grid analytics-list-grid">
            <section class="analytics-card analytics-table-card">
                <div class="analytics-card-head">
                    <div>
                        <span class="analytics-kicker">Users</span>
                        <h2>Registrations per month</h2>
                    </div>
                </div>
                <div class="metric-list">
                    <c:forEach var="row" items="${userMonths}">
                        <div class="metric-row">
                            <span><c:out value="${row.month}"/></span>
                            <div class="metric-bar"><i style="width:${totalUsers gt 0 ? (row.total * 100 / totalUsers) : 0}%;"></i></div>
                            <strong><c:out value="${row.total}"/></strong>
                        </div>
                    </c:forEach>
                    <c:if test="${empty userMonths}"><p>No user registrations yet.</p></c:if>
                </div>
            </section>
            <section class="analytics-card analytics-table-card">
                <div class="analytics-card-head">
                    <div>
                        <span class="analytics-kicker">Matches</span>
                        <h2>Matches per month</h2>
                    </div>
                </div>
                <div class="metric-list">
                    <c:forEach var="row" items="${matchMonths}">
                        <div class="metric-row">
                            <span><c:out value="${row.month}"/></span>
                            <div class="metric-bar"><i style="width:${totalMatches gt 0 ? (row.total * 100 / totalMatches) : 0}%;"></i></div>
                            <strong><c:out value="${row.total}"/></strong>
                        </div>
                    </c:forEach>
                    <c:if test="${empty matchMonths}"><p>No matches yet.</p></c:if>
                </div>
            </section>
        </div>

        <section class="analytics-card analytics-table-card feedback-card">
            <div class="analytics-card-head">
                <div>
                    <span class="analytics-kicker">Feedback</span>
                    <h2>Recent user feedback</h2>
                </div>
            </div>
            <div class="feedback-list">
                <c:forEach var="feedback" items="${recentFeedback}">
                    <div class="feedback-row">
                        <div class="feedback-rating">
                            <strong><c:out value="${feedback.rating}"/>/5</strong>
                            <span>User ID <c:out value="${feedback.userId}"/></span>
                        </div>
                        <p><c:out value="${not empty feedback.comment ? feedback.comment : 'No comment added.'}"/></p>
                    </div>
                </c:forEach>
                <c:if test="${empty recentFeedback}">
                    <p>No feedback submitted yet.</p>
                </c:if>
            </div>
        </section>
    </main>
</div>
</body>
</html>
