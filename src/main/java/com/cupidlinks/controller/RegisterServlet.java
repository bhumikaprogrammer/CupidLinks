package com.cupidlinks.controller;

import com.cupidlinks.service.UserService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Handles new user registration.
 * The servlet validates form data, checks duplicate email/phone values,
 * and passes the password to the service layer for hashing before saving.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    /**
     * Displays the registration page.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response sent back to the browser
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    /**
     * Processes the registration form and creates a new user account when all
     * validation checks pass.
     *
     * @param request HTTP request containing registration form values
     * @param response HTTP response used for forwarding or redirecting
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if redirecting or writing the response fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email       = ValidationUtil.sanitize(request.getParameter("email"));
        String phone       = ValidationUtil.sanitize(request.getParameter("phone"));
        String password    = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");
        String dobStr      = ValidationUtil.sanitize(request.getParameter("dateOfBirth"));

        // run all validation checks before touching the database
        if (ValidationUtil.isEmpty(email) || ValidationUtil.isEmpty(phone)
                || ValidationUtil.isEmpty(password) || ValidationUtil.isEmpty(dobStr)) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Please enter a valid email address.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("error", "Phone number must start with 97 or 98 and be 10 digits.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("error", "Password must be at least 8 characters and include uppercase, lowercase, number and special character.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.passwordsMatch(password, confirmPass)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        LocalDate dob;
        try {
            // Parse the date after checking required fields so users get clear messages.
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid date of birth.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isAdult(dob)) {
            request.setAttribute("error", "You must be at least 18 years old to register.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        try {
            if (userService.emailExists(email)) {
                request.setAttribute("error", "This email is already registered.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            if (userService.phoneExists(phone)) {
                request.setAttribute("error", "This phone number is already registered.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            // Password hashing is done in the service layer before insert.
            boolean success = userService.registerUser(email, phone, password);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/login?registered=true");
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("error", "A database error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }
}
