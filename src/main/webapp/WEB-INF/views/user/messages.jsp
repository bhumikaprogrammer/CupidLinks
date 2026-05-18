<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Messages - CupidLinks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<c:set var="activePage" value="messages" scope="request"/>
<jsp:include page="/WEB-INF/views/shared/user-navbar.jsp"/>

<div class="page-wrapper">
    <div class="page-intro">
        <div>
            <span class="section-kicker">Messages</span>
            <h1>Talk with your matched people</h1>
            <p>Conversations are available only after both people have shown interest.</p>
        </div>
    </div>

    <c:if test="${param.error eq 'true' or not empty error}">
        <div class="alert alert-error"><c:out value="${not empty error ? error : 'Could not send your message.'}"/></div>
    </c:if>

    <div class="messages-layout">
        <aside class="conversation-list">
            <c:choose>
                <c:when test="${not empty matchData}">
                    <c:forEach var="entry" items="${matchData}">
                        <c:set var="profile" value="${entry.profile}"/>
                        <a class="conversation-item ${selectedMatchId eq entry.match.matchId ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/messages?matchId=${entry.match.matchId}">
                            <span class="conversation-avatar">
                                <c:choose>
                                    <c:when test="${not empty profile.profilePhoto}">
                                        <img src="${pageContext.request.contextPath}/uploads/${profile.profilePhoto}" alt="Profile Photo">
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${not empty profile.fullName ? profile.fullName.substring(0,1) : 'C'}"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <span>
                                <strong><c:out value="${not empty profile.fullName ? profile.fullName : 'Matched User'}"/></strong>
                                <small><c:out value="${not empty profile.location ? profile.location : 'Start a conversation'}"/></small>
                            </span>
                        </a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-panel">No matches yet.</div>
                </c:otherwise>
            </c:choose>
        </aside>

        <section class="conversation-panel">
            <c:choose>
                <c:when test="${selectedMatchId gt 0}">
                    <div class="message-stream">
                        <c:choose>
                            <c:when test="${not empty messages}">
                                <c:forEach var="message" items="${messages}">
                                    <div class="message-bubble ${message.senderId eq sessionScope.userId ? 'mine' : 'theirs'}">
                                        <p><c:out value="${message.content}"/></p>
                                        <small><c:out value="${message.sentAt}"/></small>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-panel">No messages yet. Send the first one.</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="message-tools">
                        <form action="${pageContext.request.contextPath}/messages" method="post" class="icebreaker-form">
                            <input type="hidden" name="matchId" value="${selectedMatchId}">
                            <input type="hidden" name="action" value="iceBreaker">
                            <label for="iceBreaker">Send an ice breaker</label>
                            <div class="message-tool-row">
                                <select id="iceBreaker" name="iceBreaker" required>
                                    <option value="">Choose a quick question</option>
                                    <option value="Tea or coffee?">Tea or coffee?</option>
                                    <option value="Pizza or momo?">Pizza or momo?</option>
                                    <option value="Morning or night?">Morning or night?</option>
                                    <option value="Cats or dogs?">Cats or dogs?</option>
                                    <option value="Call or text?">Call or text?</option>
                                </select>
                                <button type="submit">Send</button>
                            </div>
                        </form>

                        <form action="${pageContext.request.contextPath}/messages" method="post" class="quick-choice-form">
                            <input type="hidden" name="matchId" value="${selectedMatchId}">
                            <input type="hidden" name="action" value="quickChoice">
                            <label for="choice">Send a quick answer</label>
                            <div class="message-tool-row">
                                <select id="choice" name="choice" required>
                                    <option value="">Choose an answer</option>
                                    <option value="Tea">Tea</option>
                                    <option value="Coffee">Coffee</option>
                                    <option value="Pizza">Pizza</option>
                                    <option value="Momo">Momo</option>
                                    <option value="Morning">Morning</option>
                                    <option value="Night">Night</option>
                                    <option value="Cats">Cats</option>
                                    <option value="Dogs">Dogs</option>
                                    <option value="Call">Call</option>
                                    <option value="Text">Text</option>
                                </select>
                                <button type="submit">Send</button>
                            </div>
                        </form>

                        <form action="${pageContext.request.contextPath}/messages" method="post" class="social-form">
                            <input type="hidden" name="matchId" value="${selectedMatchId}">
                            <input type="hidden" name="action" value="shareSocial">
                            <label>Share social account to continue talking</label>
                            <div class="message-tool-row social-row">
                                <select name="platform" required>
                                    <option value="">Platform</option>
                                    <option value="Instagram">Instagram</option>
                                    <option value="Facebook">Facebook</option>
                                    <option value="WhatsApp">WhatsApp</option>
                                    <option value="TikTok">TikTok</option>
                                    <option value="LinkedIn">LinkedIn</option>
                                </select>
                                <input type="text" name="handle" placeholder="@username or phone" required>
                                <button type="submit">Share</button>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-panel">Choose a match to open the conversation.</div>
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</div>

</body>
</html>
