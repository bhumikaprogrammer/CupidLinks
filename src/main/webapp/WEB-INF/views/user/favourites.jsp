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
    <title>Favourites - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<c:set var="activePage" value="favourites" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="page-intro">
        <div>
            <span class="section-kicker">Favourites</span>
            <h1>Keep the profiles you want to revisit</h1>
            <p>Your saved list stays here so you can come back later and decide with a clearer head.</p>
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="profiles-grid">
        <c:forEach var="profile" items="${favourites}">
            <div class="profile-card">
                <div class="profile-avatar">
                    <c:choose>
                        <c:when test="${not empty profile.profilePhoto}">
                            <img src="${pageContext.request.contextPath}/uploads/<c:out value='${profile.profilePhoto}'/>" alt="Profile Photo">
                        </c:when>
                        <c:otherwise>
                            <div class="avatar-placeholder">${profile.fullName.substring(0,1)}</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="profile-info">
                    <h3>${profile.fullName}</h3>
                    <c:if test="${not empty profile.location or not empty profile.nepaliRaasi or not empty profile.clan}">
                        <p class="profile-meta">
                            <c:if test="${not empty profile.location}">${profile.location} &bull; </c:if>
                            <c:if test="${not empty profile.nepaliRaasi}">${profile.nepaliRaasi}</c:if>
                            <c:if test="${not empty profile.clan}">
                                <c:if test="${not empty profile.nepaliRaasi}"> &bull; </c:if>
                                Clan: ${profile.clan}
                            </c:if>
                        </p>
                    </c:if>
                    <p class="profile-bio">
                        <c:choose>
                            <c:when test="${not empty profile.bio}">${profile.bio}</c:when>
                            <c:otherwise>No bio yet.</c:otherwise>
                        </c:choose>
                    </p>
                    <div class="profile-actions">
                        <form action="${pageContext.request.contextPath}/favourites" method="post">
                            <input type="hidden" name="savedUserId" value="${profile.userId}">
                            <input type="hidden" name="action" value="remove">
                            <button type="submit" class="btn-fav">Remove</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty favourites}">
            <div class="no-profiles">
                <p>No saved profiles yet. Go to Discover and save profiles you like!</p>
            </div>
        </c:if>
    </div>

</div>

</body>
</html>
