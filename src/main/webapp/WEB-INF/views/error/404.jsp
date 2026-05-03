<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/11/2026
  Time: 11:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Not Found - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="error-wrapper">
    <div class="error-card">
        <div class="heart"></div>
        <h1>404</h1>
        <h2>Page Not Found</h2>
        <p>The page you are looking for does not exist or has been moved.</p>
        <a href="${pageContext.request.contextPath}/" class="btn-home">Go Back Home</a>
    </div>
</div>
</body>
</html>

