<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/11/2026
  Time: 9:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">

        <div class="auth-header">
            <div class="heart"></div>
            <h1>CupidLinks</h1>
            <p>Create your account to get started</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error"><c:out value="${error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" class="auth-form">

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="you@example.com" required
                       value="${not empty param.email ? param.email : ''}">
            </div>

            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="text" id="phone" name="phone" placeholder="98XXXXXXXX" required
                       value="${not empty param.phone ? param.phone : ''}">
                <small>Must start with 97 or 98 and be 10 digits.</small>
            </div>

            <div class="form-group">
                <label for="dateOfBirth">Date of Birth</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" required
                       value="${not empty param.dateOfBirth ? param.dateOfBirth : ''}">
                <small>You must be at least 18 years old to register.</small>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Min 8 characters" required>
                <small>Must include uppercase, lowercase, number and special character.</small>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Repeat your password" required>
            </div>

            <button type="submit" class="btn-primary">Create Account</button>

        </form>

        <div class="auth-footer">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
        </div>

    </div>
</div>
</body>
</html>
