package com.cupidlinks.controller;

import com.cupidlinks.model.User;
import com.cupidlinks.service.UserService;
import com.cupidlinks.util.PasswordUtil;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Handles user login for CupidLinks.
 * The servlet validates login input, verifies the BCrypt password hash,
 * creates a session, optionally stores a remember-me cookie, and redirects
 * users according to their role.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    /**
     * Displays the login page.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response sent back to the browser
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    /**
     * Processes the login form submission.
     *
     * @param request HTTP request containing email, password, and remember-me value
     * @param response HTTP response used for forwarding or redirecting
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if redirecting or writing the response fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email    = ValidationUtil.sanitize(request.getParameter("email"));
        String password = request.getParameter("password");

        if (ValidationUtil.isEmpty(email) || ValidationUtil.isEmpty(password)) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        try {
            // Look up the user first, then compare the entered password with the stored hash.
            User user = userService.findByEmail(email);

            if (user == null || !PasswordUtil.verify(password, user.getPasswordHash())) {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                return;
            }

            if (user.getStatus().equals("suspended")) {
                request.setAttribute("error", "Your account has been suspended. Please contact support.");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                return;
            }

            if (user.getStatus().equals("deleted")) {
                request.setAttribute("error", "This account no longer exists.");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                return;
            }

            // Store only the details needed for authorization and page display.
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(30 * 60);

            // safely increment site-wide visit counter - null check prevents crash
            try {
                ServletContext context = getServletContext();
                Object countObj = context.getAttribute("visitCount");
                int count = (countObj != null) ? (int) countObj : 0;
                context.setAttribute("visitCount", count + 1);
            } catch (Exception ignored) {}

            // remember me cookie - lasts 7 days
            String rememberMe = request.getParameter("rememberMe");
            if (rememberMe != null) {
                Cookie cookie = new Cookie("userEmail", email);
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }

            // send admin to dashboard, regular users to discover page
            if (user.getRole().equals("admin")) {
                request.getRequestDispatcher("/admin/dashboard").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/discover");
            }

        } catch (SQLException e) {
            request.setAttribute("error", "A database error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }
}
