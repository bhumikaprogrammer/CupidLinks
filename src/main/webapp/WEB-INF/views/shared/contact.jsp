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
    <title>Contact - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css?v=6">
</head>
<body class="public-info-page">

<c:choose>
    <c:when test="${not empty sessionScope.userId}">
        <c:set var="activePage" value="contact" scope="request"/>
        <jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>
    </c:when>
    <c:otherwise>
        <nav class="landing-nav">
            <a href="${pageContext.request.contextPath}/" class="nav-brand">
                <div class="heart-small"></div>
                <span>CupidLinks</span>
            </a>
            <div class="nav-actions">
                <a href="${pageContext.request.contextPath}/" class="nav-about">Home</a>
                <a href="${pageContext.request.contextPath}/about" class="nav-about">About</a>
                <a href="${pageContext.request.contextPath}/contact" class="nav-about">Contact</a>
                <a href="${pageContext.request.contextPath}/login" class="btn-login">Sign In</a>
                <a href="${pageContext.request.contextPath}/register" class="btn-register">Get Started</a>
            </div>
        </nav>
    </c:otherwise>
</c:choose>

<div class="page-wrapper">
    <div class="contact-card">
        <h2>Contact CupidLinks</h2>
        <p class="contact-sub">For questions about the project, the platform, or general communication, reach out to the CupidLinks team.</p>

        <c:if test="${param.sent eq 'true'}">
            <div class="alert alert-success">Your message has been sent. We will get back to you soon.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:choose>
            <c:when test="${not empty sessionScope.userId}">
                <form action="${pageContext.request.contextPath}/contact" method="post" class="contact-form">
                    <div class="form-group">
                        <label for="subject">Subject</label>
                        <input type="text" id="subject" name="subject" placeholder="What would you like to ask?" required>
                    </div>
                    <div class="form-group">
                        <label for="message">Message</label>
                        <textarea id="message" name="message" placeholder="Write your message here..." rows="5" required></textarea>
                    </div>
                    <button type="submit" class="btn-primary">Send Message</button>
                </form>
            </c:when>
            <c:otherwise>
                <div class="contact-info-list">
                    <div>
                        <span>Email</span>
                        <strong>cupidlinks@iic.edu.np</strong>
                    </div>
                    <div>
                        <span>Location</span>
                        <strong>Nepal</strong>
                    </div>
                    <div>
                        <span>Member help</span>
                        <strong>Sign in to send account support requests.</strong>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>

