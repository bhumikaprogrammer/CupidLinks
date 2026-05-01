package com.cupidlinks.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Protects normal user pages from being opened without login.
 * If there is no active session, the user is redirected to the login page.
 */
@WebFilter(urlPatterns = {"/discover", "/profile", "/matches", "/favourites", "/interest", "/report", "/feedback", "/support", "/about", "/contact"})
public class AuthFilter implements Filter {

    /**
     * Checks whether the current request has a valid logged-in session.
     *
     * @param request incoming servlet request
     * @param response outgoing servlet response
     * @param chain next filter or servlet in the request chain
     * @throws IOException if redirecting or continuing the request fails
     * @throws ServletException if the servlet container cannot process the request
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("userId") != null;

        if (!loggedIn) {
            // Without a session, protected pages should not be visible.
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
