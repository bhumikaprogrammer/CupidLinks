<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/12/2026
  Time: 9:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Matches - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<c:set var="activePage" value="matches" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="page-intro">
        <div>
            <span class="section-kicker">Matches</span>
            <h1>People who liked you back</h1>
            <p>When interest is mutual, this is where your strongest connections start to stand out.</p>
        </div>
    </div>

    <c:if test="${param.newMatch eq 'true'}">
        <div class="alert alert-success">You have a new match!</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <div class="profiles-grid">
        <c:choose>
            <c:when test="${not empty matchData}">
                <c:forEach var="entry" items="${matchData}">
                    <c:set var="profile" value="${entry.profile}"/>
                    <div class="profile-card">
                        <div class="profile-avatar">
                            <c:choose>
                                <c:when test="${not empty profile.profilePhoto}">
                                    <img src="${pageContext.request.contextPath}/uploads/<c:out value='${profile.profilePhoto}'/>" alt="Profile Photo">
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${not empty profile.fullName}">
                                            <div class="avatar-placeholder"><c:out value="${profile.fullName.substring(0,1)}"/></div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="avatar-placeholder">&#9829;</div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="profile-info">
                            <c:choose>
                                <c:when test="${not empty profile.fullName}">
                                    <h3><c:out value="${profile.fullName}"/></h3>
                                    <c:if test="${not empty profile.location or not empty profile.nepaliRaasi or not empty profile.clan}">
                                        <p class="profile-meta">
                                            <c:if test="${not empty profile.location}"><c:out value="${profile.location}"/> &bull; </c:if>
                                            <c:if test="${not empty profile.nepaliRaasi}"><c:out value="${profile.nepaliRaasi}"/></c:if>
                                            <c:if test="${not empty profile.clan}">
                                                <c:if test="${not empty profile.nepaliRaasi}"> &bull; </c:if>
                                                Clan: <c:out value="${profile.clan}"/>
                                            </c:if>
                                        </p>
                                    </c:if>
                                    <p class="profile-bio">
                                        <c:choose>
                                            <c:when test="${not empty profile.bio}"><c:out value="${profile.bio}"/></c:when>
                                            <c:otherwise>No bio yet.</c:otherwise>
                                        </c:choose>
                                    </p>
                                </c:when>
                                <c:otherwise>
                                    <h3>Matched User</h3>
                                    <p class="profile-bio">This user has not set up their profile yet.</p>
                                </c:otherwise>
                            </c:choose>
                            <div class="match-tag">Matched</div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-profiles">
                    <p>No matches yet. Go to Discover and start liking profiles!</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
