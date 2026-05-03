<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/discover" class="nav-brand">
        <div class="heart-small"></div>
        <span>CupidLinks</span>
    </a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/discover" class="${activePage eq 'discover' ? 'active' : ''}">Discover</a>
        <a href="${pageContext.request.contextPath}/profile" class="${activePage eq 'profile' ? 'active' : ''}">My Profile</a>
        <a href="${pageContext.request.contextPath}/matches" class="${activePage eq 'matches' ? 'active' : ''}">Matches</a>
        <a href="${pageContext.request.contextPath}/favourites" class="${activePage eq 'favourites' ? 'active' : ''}">Favourites</a>
        <a href="${pageContext.request.contextPath}/about" class="${activePage eq 'about' ? 'active' : ''}">About</a>
        <a href="${pageContext.request.contextPath}/contact" class="${activePage eq 'contact' ? 'active' : ''}">Contact</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
    <button type="button" class="nav-hamburger" aria-label="Toggle navigation" aria-controls="mobileNav" aria-expanded="false"
            onclick="toggleNav(this)">
        <span></span><span></span><span></span>
    </button>
</nav>

<div class="mobile-nav" id="mobileNav">
    <a href="${pageContext.request.contextPath}/discover" class="${activePage eq 'discover' ? 'active' : ''}">Discover</a>
    <a href="${pageContext.request.contextPath}/profile" class="${activePage eq 'profile' ? 'active' : ''}">My Profile</a>
    <a href="${pageContext.request.contextPath}/matches" class="${activePage eq 'matches' ? 'active' : ''}">Matches</a>
    <a href="${pageContext.request.contextPath}/favourites" class="${activePage eq 'favourites' ? 'active' : ''}">Favourites</a>
    <a href="${pageContext.request.contextPath}/about" class="${activePage eq 'about' ? 'active' : ''}">About</a>
    <a href="${pageContext.request.contextPath}/contact" class="${activePage eq 'contact' ? 'active' : ''}">Contact</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

<script>
    function toggleNav(button) {
        const mobileNav = document.getElementById('mobileNav');
        if (!mobileNav) {
            return;
        }

        const isOpen = mobileNav.classList.toggle('open');
        if (button) {
            button.setAttribute('aria-expanded', isOpen ? 'true' : 'false');
        }
    }
</script>
