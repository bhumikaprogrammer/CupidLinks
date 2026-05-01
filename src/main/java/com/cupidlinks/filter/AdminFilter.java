package com.cupidlinks.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Restricts all admin URLs to users whose session role is "admin".
 * This is the main role-based authorization check for the admin dashboard.
 */
@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    /**
     * Allows the request only when the logged-in user has the admin role.
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

        HttpServletRequest httpRequest   = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session              = httpRequest.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("userId") != null;
        boolean isAdmin  = loggedIn && "admin".equals(session.getAttribute("role"));

        if (!isAdmin) {
            // Non-admin users are sent back to login instead of seeing admin pages.
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
