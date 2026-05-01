<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/11/2026
  Time: 10:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css?v=2">
</head>
<body>

<%-- store admin email from session into a page variable --%>
<c:set var="adminEmail" value="${sessionScope.email}" scope="page"/>

<div class="admin-wrapper">

    <aside class="sidebar">
        <div class="sidebar-brand">
            <div class="heart-small"></div>
            <span>CupidLinks</span>
        </div>
        <nav class="sidebar-nav">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item">Reports</a>
            <a href="${pageContext.request.contextPath}/admin/support" class="nav-item">Support</a>
            <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">Logout</a>
        </nav>
    </aside>

    <main class="admin-main">

        <div class="admin-header">
            <h1>Admin Dashboard</h1>
            <%-- sessionScope explicitly reads from session scope --%>
            <span>Welcome, <strong><c:out value="${adminEmail}"/></strong></span>
        </div>

        <%-- param.success and param.error read query string after redirect --%>
        <c:choose>
            <c:when test="${param.success eq 'suspend'}">
                <div class="alert alert-success">User suspended successfully.</div>
            </c:when>
            <c:when test="${param.success eq 'approve'}">
                <div class="alert alert-success">User approved successfully.</div>
            </c:when>
            <c:when test="${param.success eq 'delete'}">
                <div class="alert alert-success">User deleted successfully.</div>
            </c:when>
            <c:when test="${param.error eq 'true'}">
                <div class="alert alert-error">Something went wrong. Please try again.</div>
            </c:when>
        </c:choose>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number"><c:out value="${totalUsers}"/></div>
                <div class="stat-label">Total Users</div>
            </div>
            <div class="stat-card">
                <div class="stat-number"><c:out value="${approvedUsers}"/></div>
                <div class="stat-label">Active Users</div>
            </div>
            <div class="stat-card">
                <div class="stat-number"><c:out value="${suspendedUsers}"/></div>
                <div class="stat-label">Suspended</div>
            </div>
            <div class="stat-card">
                <div class="stat-number"><c:out value="${totalMatches}"/></div>
                <div class="stat-label">Total Matches</div>
            </div>
        </div>

        <div class="analytics-grid">
            <section class="analytics-card analytics-card-wide">
                <div class="analytics-copy">
                    <span class="analytics-kicker">Community Health</span>
                    <h2>User status overview</h2>
                    <p>Active accounts should stay high while suspended accounts stay low through quick moderation.</p>
                </div>
                <div class="status-bars">
                    <div class="status-row">
                        <div class="status-row-head">
                            <span>Active users</span>
                            <strong><c:out value="${approvedUsers}"/></strong>
                        </div>
                        <div class="bar-track">
                            <div class="bar-fill bar-fill-active"
                                 style="width:${totalUsers gt 0 ? (approvedUsers * 100 / totalUsers) : 0}%;"></div>
                        </div>
                    </div>
                    <div class="status-row">
                        <div class="status-row-head">
                            <span>Suspended users</span>
                            <strong><c:out value="${suspendedUsers}"/></strong>
                        </div>
                        <div class="bar-track">
                            <div class="bar-fill bar-fill-warning"
                                 style="width:${totalUsers gt 0 ? (suspendedUsers * 100 / totalUsers) : 0}%;"></div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="analytics-card match-card">
                <div>
                    <span class="analytics-kicker">Connections</span>
                    <h2><c:out value="${totalMatches}"/> matches</h2>
                    <p>Mutual likes converted into matches.</p>
                </div>
                <div class="match-orbit" aria-hidden="true">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </section>

            <section class="analytics-card moderation-card">
                <span class="analytics-kicker">Moderation</span>
                <h2>Review focus</h2>
                <div class="moderation-list">
                    <div>
                        <span>Keep profiles genuine</span>
                        <strong>Ongoing</strong>
                    </div>
                    <div>
                        <span>Handle unsafe accounts</span>
                        <strong><c:out value="${suspendedUsers}"/> flagged</strong>
                    </div>
                </div>
            </section>
        </div>

        <div class="table-section">

            <div class="table-toolbar">
                <h2>User Management</h2>
                <form action="${pageContext.request.contextPath}/admin/dashboard" method="get" class="search-form">
                    <%-- param.search and param.sort read current query string values --%>
                    <input type="text" name="search" placeholder="Search by email or phone..."
                           value="${not empty param.search ? param.search : ''}">
                    <select name="sort">
                        <option value="">Default</option>
                        <option value="name_asc"  ${param.sort eq 'name_asc'  ? 'selected' : ''}>Name A - Z</option>
                        <option value="name_desc" ${param.sort eq 'name_desc' ? 'selected' : ''}>Name Z - A</option>
                        <option value="status"    ${param.sort eq 'status'    ? 'selected' : ''}>By Status</option>
                    </select>
                    <button type="submit">Search</button>
                </form>
            </div>

            <div class="table-wrap">
                <table class="admin-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty users}">
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td><c:out value="${user.userId}"/></td>
                                    <td><c:out value="${user.email}"/></td>
                                        <%-- not empty null check prevents blank cell errors --%>
                                    <td><c:out value="${not empty user.phone ? user.phone : '-'}"/></td>
                                    <td>
                                            <span class="badge badge-${user.status}">
                                                <c:out value="${user.status}"/>
                                            </span>
                                    </td>
                                    <td class="action-btns">
                                        <c:if test="${user.status eq 'approved'}">
                                            <form action="${pageContext.request.contextPath}/admin/dashboard" method="post" style="display:inline">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <input type="hidden" name="action" value="suspend">
                                                <button type="submit" class="btn btn-warning">Suspend</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${user.status eq 'suspended'}">
                                            <form action="${pageContext.request.contextPath}/admin/dashboard" method="post" style="display:inline">
                                                <input type="hidden" name="userId" value="${user.userId}">
                                                <input type="hidden" name="action" value="approve">
                                                <button type="submit" class="btn btn-success">Approve</button>
                                            </form>
                                        </c:if>
                                        <form action="${pageContext.request.contextPath}/admin/dashboard" method="post" style="display:inline">
                                            <input type="hidden" name="userId" value="${user.userId}">
                                            <input type="hidden" name="action" value="delete">
                                            <button type="submit" class="btn-delete"
                                                    onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" style="text-align:center;padding:30px;color:#888;">No users found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>

        </div>

    </main>

</div>

</body>
</html>
