<%--
  Created by IntelliJ IDEA.
  User: Bhumika
  Date: 4/05/2026
  Time: 9:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (session.getAttribute("userId") != null) {
        String role = (String) session.getAttribute("role");
        if ("admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/discover");
        }
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CupidLinks - Find Your Person</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
</head>
<body>

<!-- Scattered floating hearts background -->
<div class="hearts-bg" aria-hidden="true">
    <span class="heart" style="left:4%;  font-size:14px; animation-duration:14s; animation-delay:0s;">♥</span>
    <span class="heart" style="left:12%; font-size:20px; animation-duration:18s; animation-delay:3s;">♥</span>
    <span class="heart" style="left:22%; font-size:11px; animation-duration:20s; animation-delay:7s;">♥</span>
    <span class="heart" style="left:33%; font-size:16px; animation-duration:16s; animation-delay:1s;">♥</span>
    <span class="heart" style="left:45%; font-size:10px; animation-duration:22s; animation-delay:5s;">♥</span>
    <span class="heart" style="left:56%; font-size:18px; animation-duration:17s; animation-delay:9s;">♥</span>
    <span class="heart" style="left:67%; font-size:13px; animation-duration:19s; animation-delay:2s;">♥</span>
    <span class="heart" style="left:77%; font-size:22px; animation-duration:21s; animation-delay:6s;">♥</span>
    <span class="heart" style="left:87%; font-size:15px; animation-duration:15s; animation-delay:4s;">♥</span>
    <span class="heart" style="left:95%; font-size:10px; animation-duration:23s; animation-delay:8s;">♥</span>
    <span class="heart" style="left:8%;  font-size:19px; animation-duration:25s; animation-delay:12s;">♥</span>
    <span class="heart" style="left:38%; font-size:9px;  animation-duration:13s; animation-delay:10s;">♥</span>
    <span class="heart" style="left:62%; font-size:14px; animation-duration:20s; animation-delay:15s;">♥</span>
    <span class="heart" style="left:82%; font-size:17px; animation-duration:18s; animation-delay:11s;">♥</span>
</div>

<nav class="landing-nav">
    <div class="nav-brand">
        <div class="heart-small"></div>
        <span>CupidLinks</span>
    </div>
    <div class="nav-actions">
        <a href="${pageContext.request.contextPath}/login" class="btn-login">Sign In</a>
        <a href="${pageContext.request.contextPath}/register" class="btn-register">Get Started</a>
    </div>
    <div class="hamburger" onclick="toggleMenu()">
        <span></span>
        <span></span>
        <span></span>
    </div>
</nav>

<div class="mobile-menu" id="mobileMenu">
    <a href="${pageContext.request.contextPath}/login">Sign In</a>
    <a href="${pageContext.request.contextPath}/register">Create Account</a>
    <a href="${pageContext.request.contextPath}/about">About</a>
</div>

<section class="hero">
    <div class="hero-content">
        <div class="hero-tag">Made in Nepal</div>
        <h1>Meet people who<br><span>actually feel real.</span></h1>
        <p>CupidLinks is a platform designed for young adults in Nepal. Honest profiles, clear intentions, and room for genuine connection.</p>
        <div class="hero-btns">
            <a href="${pageContext.request.contextPath}/register" class="btn-primary">Find someone genuine</a>
            <a href="${pageContext.request.contextPath}/login" class="btn-outline">Sign in</a>
        </div>
    </div>
    <div class="hero-visual">
        <div class="hero-showcase">
            <div class="hero-photo-stack">
                <div class="hero-photo-frame hero-photo-frame-connection">
                    <img
                            src="${pageContext.request.contextPath}/images/landing/hero-connection.png"
                            alt="Connection preview for the CupidLinks landing page">
                </div>
                <div class="hero-photo-frame hero-photo-frame-main">
                    <img
                            src="${pageContext.request.contextPath}/images/landing/hero-couple.png"
                            alt="Couple preview for the CupidLinks landing page">
                </div>
            </div>

            <div class="hero-sideboard">
                <span class="hero-mini-label">Meet CupidLinks</span>
                <h3>Small details make the whole profile feel human.</h3>
                <div class="hero-sideboard-grid">
                    <div class="hero-sideboard-card">
                        <strong>Say what you're looking for</strong>
                        <span>Serious, casual, or still figuring it out.</span>
                    </div>
                    <div class="hero-sideboard-card">
                        <strong>No copy-paste bios</strong>
                        <span>Short, honest intros instead of empty one-liners.</span>
                    </div>
                    <div class="hero-sideboard-card">
                        <strong>See who's nearby</strong>
                        <span>People near you, without making it complicated.</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="stats-bar">
    <div class="stat-item">
        <span class="stat-num">100%</span>
        <span class="stat-text">Free to join</span>
    </div>
    <div class="stat-divider"></div>
    <div class="stat-item">
        <span class="stat-num">18+</span>
        <span class="stat-text">Adults only</span>
    </div>
    <div class="stat-divider"></div>
    <div class="stat-item">
        <span class="stat-num">Safe</span>
        <span class="stat-text">Block or report anytime</span>
    </div>
    <div class="stat-divider"></div>
    <div class="stat-item">
        <span class="stat-num">Nepal</span>
        <span class="stat-text">Built for Nepal</span>
    </div>
</section>

<section class="how-it-works">
    <div class="section-heart">&#9829;</div>
    <h2>Simple from the start</h2>
    <p class="section-sub">No complicated matchmaking. Just you, your story, and the people you might like.</p>
    <div class="steps">
        <div class="step">
            <div class="step-num">1</div>
            <h3>Create your profile</h3>
            <p>Add a few photos, write about yourself, and tell others what you are hoping for.</p>
        </div>
        <div class="step">
            <div class="step-num">2</div>
            <h3>Discover people</h3>
            <p>Browse real profiles near your city, age, and interests.</p>
        </div>
        <div class="step">
            <div class="step-num">3</div>
            <h3>Connect when it feels right</h3>
            <p>Like or save a profile. It's your choice, all at your own pace.</p>
        </div>
    </div>
</section>

<section class="values">
    <div class="section-heart values-heart">&#9829;</div>
    <div class="value-card">
        <div class="value-icon">&#10084;</div>
        <h3>Your privacy is yours</h3>
        <p>Control what you share and keep profile settings in your own hands.</p>
    </div>
    <div class="value-card">
        <div class="value-icon">&#10084;</div>
        <h3>No fake accounts</h3>
        <p>We use account checks to keep our community real, respectful, and active.</p>
    </div>
    <div class="value-card">
        <div class="value-icon">&#10084;</div>
        <h3>Culturally aware</h3>
        <p>Optional Nepali context fields help improve compatibility, but nothing is forced.</p>
    </div>
</section>

<section class="cta-section">
    <div class="cta-box">
        <div class="heart-large"></div>
        <h2>Ready to meet someone real?</h2>
        <p>Join CupidLinks today - a safe, local space to connect with compatible and caring people.</p>
        <a href="${pageContext.request.contextPath}/register" class="btn-primary">Start for free</a>
    </div>
</section>

<footer class="landing-footer">
    <div class="footer-brand">
        <div class="heart-small"></div>
        <span>CupidLinks</span>
    </div>
    <div class="footer-links">
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        <a href="${pageContext.request.contextPath}/login">Sign In</a>
        <a href="${pageContext.request.contextPath}/register">Register</a>
    </div>
    <p class="footer-copy">Made with love in Nepal &copy; 2026 CupidLinks</p>
</footer>

<script>
    function toggleMenu() {
        var menu = document.getElementById("mobileMenu");
        menu.classList.toggle("open");
    }
</script>

</body>
</html>
