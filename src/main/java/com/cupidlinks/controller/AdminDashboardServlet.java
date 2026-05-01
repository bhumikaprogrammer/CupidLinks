package com.cupidlinks.controller;

import com.cupidlinks.model.User;
import com.cupidlinks.service.AdminService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for the admin dashboard.
 * It loads dashboard analytics, handles search/sort filters, and processes
 * admin actions such as suspend, approve, and delete.
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final AdminService adminService = new AdminService();

    /**
     * Displays the admin dashboard with users and analytics.
     *
     * @param request HTTP request containing optional search and sort parameters
     * @param response HTTP response used to forward to the dashboard JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = ValidationUtil.sanitize(request.getParameter("search"));
        String sort   = ValidationUtil.sanitize(request.getParameter("sort"));

        try {
            List<User> users;

            if (!search.isEmpty()) {
                users = adminService.searchUsers(search, sort);
            } else {
                users = adminService.getAllUsers(sort);
            }

            // The dropdown also acts as a quick status filter for admins.
            if ("suspended".equals(sort)) {
                users.removeIf(u -> !u.getStatus().equals("suspended"));
            } else if ("approved".equals(sort)) {
                users.removeIf(u -> !u.getStatus().equals("approved"));
            }

            request.setAttribute("users", users);
            request.setAttribute("totalUsers", adminService.getTotalUsers());
            request.setAttribute("approvedUsers", adminService.getApprovedUsers());
            request.setAttribute("suspendedUsers", adminService.getSuspendedUsers());
            request.setAttribute("totalMatches", adminService.getTotalMatches());
            request.setAttribute("search", search);
            request.setAttribute("sort", sort);

            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load dashboard data.");
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        }
    }

    /**
     * Handles admin actions submitted from the user table.
     *
     * @param request HTTP request containing action and userId
     * @param response HTTP response used for redirecting back to the dashboard
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = ValidationUtil.sanitize(request.getParameter("action"));
        String userIdStr = ValidationUtil.sanitize(request.getParameter("userId"));

        if (ValidationUtil.isEmpty(action) || ValidationUtil.isEmpty(userIdStr)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);

            // Only known actions are accepted from the dashboard forms.
            switch (action) {
                case "suspend":
                    adminService.updateUserStatus(userId, "suspended");
                    break;
                case "approve":
                    adminService.updateUserStatus(userId, "approved");
                    break;
                case "delete":
                    adminService.deleteUser(userId);
                    break;
            }

            response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=" + action);

        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=true");
        }
    }
}
