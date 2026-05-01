<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/11/2026
  Time: 9:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">

        <div class="auth-header">
            <div class="heart"></div>
            <h1>CupidLinks</h1>
            <p>Welcome back, sign in to continue</p>
        </div>

        <c:if test="${param.registered eq 'true'}">
            <div class="alert alert-success">Account created successfully! You can now sign in.</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-error"><c:out value="${error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="you@example.com" required
                       value="${not empty cookie.userEmail ? cookie.userEmail.value : ''}">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Your password" required>
            </div>

            <div class="form-group form-check">
                <input type="checkbox" id="rememberMe" name="rememberMe">
                <label for="rememberMe">Remember me for 7 days</label>
            </div>

            <button type="submit" class="btn-primary">Sign In</button>

        </form>

        <div class="auth-footer">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
        </div>

    </div>
</div>
</body>
</html>
