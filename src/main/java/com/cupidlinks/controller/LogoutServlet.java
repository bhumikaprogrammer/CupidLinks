package com.cupidlinks.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controller for ending a user's logged-in session.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Invalidates the current session, clears the remember-me cookie, and redirects to login.
     *
     * @param request HTTP request containing the current session
     * @param response HTTP response used to redirect to the login page
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // destroy the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // clear the remember me cookie if it exists
        Cookie cookie = new Cookie("userEmail", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
