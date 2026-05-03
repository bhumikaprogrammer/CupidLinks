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
    <title>Access Denied - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="error-wrapper">
    <div class="error-card">
        <div class="heart"></div>
        <h1>403</h1>
        <h2>Access Denied</h2>
        <p>You do not have permission to access this page.</p>
        <a href="${pageContext.request.contextPath}/" class="btn-home">Go Back Home</a>
    </div>
</div>
</body>
</html>


