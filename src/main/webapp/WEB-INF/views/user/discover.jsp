<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/11/2026
  Time: 11:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Discover - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<c:set var="currentUserId" value="${sessionScope.userId}" scope="page"/>
<c:set var="activePage" value="discover" scope="request"/>

<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="page-intro">
        <div>
            <span class="section-kicker">Discover</span>
            <h1>Find people who feel right for you</h1>
            <p>Browse real profiles and narrow the list by location, gender, raasi, and recency without the clutter.</p>
        </div>
    </div>

    <c:if test="${param.liked eq 'true'}">
        <div class="alert alert-success">Interest sent successfully!</div>
    </c:if>
    <c:if test="${param.reported eq 'true'}">
        <div class="alert alert-success">User reported. Our team will review it.</div>
    </c:if>
    <c:if test="${param.error eq 'true'}">
        <div class="alert alert-error">Something went wrong. Please try again.</div>
    </c:if>

    <div class="filter-bar">
        <form action="${pageContext.request.contextPath}/discover" method="get" class="filter-form">
            <input type="text" name="location" placeholder="Search by location..."
                   value="${not empty param.location ? param.location : ''}">
            <select name="gender">
                <option value="">Any Gender</option>
                <option value="male"              ${param.gender eq 'male'              ? 'selected' : ''}>Male</option>
                <option value="female"            ${param.gender eq 'female'            ? 'selected' : ''}>Female</option>
                <option value="non-binary"        ${param.gender eq 'non-binary'        ? 'selected' : ''}>Non-binary</option>
                <option value="prefer_not_to_say" ${param.gender eq 'prefer_not_to_say' ? 'selected' : ''}>Prefer not to say</option>
            </select>
            <select name="raasi">
                <option value="">Any Raasi</option>
                <option value="Mesh"      ${param.raasi eq 'Mesh'      ? 'selected' : ''}>Mesh</option>
                <option value="Brish"     ${param.raasi eq 'Brish'     ? 'selected' : ''}>Brish</option>
                <option value="Mithun"    ${param.raasi eq 'Mithun'    ? 'selected' : ''}>Mithun</option>
                <option value="Karkat"    ${param.raasi eq 'Karkat'    ? 'selected' : ''}>Karkat</option>
                <option value="Singh"     ${param.raasi eq 'Singh'     ? 'selected' : ''}>Singh</option>
                <option value="Kanya"     ${param.raasi eq 'Kanya'     ? 'selected' : ''}>Kanya</option>
                <option value="Tula"      ${param.raasi eq 'Tula'      ? 'selected' : ''}>Tula</option>
                <option value="Brishchik" ${param.raasi eq 'Brishchik' ? 'selected' : ''}>Brishchik</option>
                <option value="Dhanu"     ${param.raasi eq 'Dhanu'     ? 'selected' : ''}>Dhanu</option>
                <option value="Makar"     ${param.raasi eq 'Makar'     ? 'selected' : ''}>Makar</option>
                <option value="Kumbha"    ${param.raasi eq 'Kumbha'    ? 'selected' : ''}>Kumbha</option>
                <option value="Meen"      ${param.raasi eq 'Meen'      ? 'selected' : ''}>Meen</option>
            </select>
            <select name="sort">
                <option value="newest" ${param.sort eq 'newest' ? 'selected' : ''}>Newest First</option>
                <option value="oldest" ${param.sort eq 'oldest' ? 'selected' : ''}>Oldest First</option>
            </select>
            <button type="submit">Filter</button>
            <a href="${pageContext.request.contextPath}/discover" class="btn-clear">Clear</a>
        </form>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <div class="profiles-grid">
        <c:choose>
            <c:when test="${not empty profiles}">
                <c:forEach var="profile" items="${profiles}">
                    <div class="profile-card">
                        <div class="profile-avatar">
                            <c:choose>
                                <c:when test="${not empty profile.profilePhoto}">
                                    <img src="${pageContext.request.contextPath}/uploads/<c:out value='${profile.profilePhoto}'/>" alt="Profile Photo">
                                </c:when>
                                <c:otherwise>
                                    <div class="avatar-placeholder">
                                        <c:out value="${profile.fullName.substring(0,1)}"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="profile-info">
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
                            <div class="profile-actions">
                                <form action="${pageContext.request.contextPath}/interest" method="post" class="profile-action-primary">
                                    <input type="hidden" name="receiverId" value="${profile.userId}">
                                    <button type="submit" class="btn-like">Like</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/favourites" method="post">
                                    <input type="hidden" name="savedUserId" value="${profile.userId}">
                                    <button type="submit" class="btn-fav">Save</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-profiles">
                    <p>No profiles found. Try adjusting your filters or check back later.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
