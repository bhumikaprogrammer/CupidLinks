<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css?v=6">
</head>
<body>

<c:set var="activePage" value="feedback" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="feedback-shell">
        <div class="feedback-hero">
            <span class="section-kicker">Feedback</span>
            <h1>Help CupidLinks get better</h1>
            <p>Share what felt smooth, what felt confusing, and what would make matching in Nepal feel more natural.</p>
        </div>

        <c:if test="${param.success eq 'true'}">
            <div class="alert alert-success">Thank you. Your feedback was saved.</div>
        </c:if>
        <c:if test="${param.error eq 'true'}">
            <div class="alert alert-error">Please choose a rating from 1 to 5.</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/feedback" method="post" class="feedback-form">
            <fieldset class="rating-group">
                <legend>How was your experience?</legend>
                <div class="rating-options">
                    <label>
                        <input type="radio" name="rating" value="5" required>
                        <span>5</span>
                        <small>Excellent</small>
                    </label>
                    <label>
                        <input type="radio" name="rating" value="4">
                        <span>4</span>
                        <small>Good</small>
                    </label>
                    <label>
                        <input type="radio" name="rating" value="3">
                        <span>3</span>
                        <small>Okay</small>
                    </label>
                    <label>
                        <input type="radio" name="rating" value="2">
                        <span>2</span>
                        <small>Rough</small>
                    </label>
                    <label>
                        <input type="radio" name="rating" value="1">
                        <span>1</span>
                        <small>Poor</small>
                    </label>
                </div>
            </fieldset>

            <div class="feedback-field">
                <label for="comment">Your comment</label>
                <textarea id="comment" name="comment" rows="6" placeholder="Tell us what worked well and what should improve."></textarea>
            </div>

            <div class="feedback-actions">
                <button type="submit">Submit Feedback</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
