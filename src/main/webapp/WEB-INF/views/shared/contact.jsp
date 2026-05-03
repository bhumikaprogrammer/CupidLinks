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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css?v=2">
</head>
<body>

<c:set var="activePage" value="contact" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="contact-card">
        <h2>Contact & Support</h2>
        <p class="contact-sub">Have a question or need help? Send us a message and our team will get back to you.</p>

        <c:if test="${param.sent eq 'true'}">
            <div class="alert alert-success">Your message has been sent. We will get back to you soon.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/contact" method="post" class="contact-form">
            <div class="form-group">
                <label for="subject">Subject</label>
                <input type="text" id="subject" name="subject" placeholder="What is your message about?" required>
            </div>
            <div class="form-group">
                <label for="message">Message</label>
                <textarea id="message" name="message" placeholder="Write your message here..." rows="5" required></textarea>
            </div>
            <button type="submit" class="btn-primary">Send Message</button>
        </form>
    </div>
</div>

</body>
</html>

