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
    <title>About - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css?v=2">
</head>
<body>

<c:set var="activePage" value="about" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="about-hero">
        <div class="heart-large"></div>
        <h1>About CupidLinks</h1>
        <p>A safe and respectful dating platform built for Nepal.</p>
    </div>

    <div class="about-grid">
        <div class="about-card">
            <h3>Our Mission</h3>
            <p>CupidLinks was created to give young adults in Nepal a safe, inclusive and culturally aware space to find meaningful connections. We believe everyone deserves to find companionship on their own terms.</p>
        </div>
        <div class="about-card">
            <h3>Safety First</h3>
            <p>We take user safety seriously. Every account is protected with encrypted passwords, profile visibility controls, and a reporting system so you can flag any inappropriate behaviour directly to our admin team.</p>
        </div>
        <div class="about-card">
            <h3>Cultural Sensitivity</h3>
            <p>Nepal is a beautifully diverse country. CupidLinks respects that diversity by allowing users to share optional cultural details and by never discriminating based on background or community.</p>
        </div>
        <div class="about-card">
            <h3>Your Privacy</h3>
            <p>You are always in control. You can choose who sees your profile, block users, and remove your account at any time. We never share your personal information with third parties.</p>
        </div>
    </div>
</div>

</body>
</html>

