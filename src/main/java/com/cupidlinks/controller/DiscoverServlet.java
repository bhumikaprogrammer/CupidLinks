package com.cupidlinks.controller;

import com.cupidlinks.model.Profile;
import com.cupidlinks.service.ProfileService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for browsing discoverable user profiles.
 */
@WebServlet("/discover")
public class DiscoverServlet extends HttpServlet {

    private final ProfileService profileService = new ProfileService();

    /**
     * Loads public profiles using optional gender, location, raasi, and sort filters.
     *
     * @param request HTTP request containing filter parameters and session data
     * @param response HTTP response used to forward to the discover JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int currentUserId = (int) session.getAttribute("userId");

        String gender   = ValidationUtil.sanitize(request.getParameter("gender"));
        String location = ValidationUtil.sanitize(request.getParameter("location"));
        String raasi    = ValidationUtil.sanitize(request.getParameter("raasi"));
        String sort     = ValidationUtil.sanitize(request.getParameter("sort"));

        try {
            List<Profile> profiles = profileService.discoverProfiles(currentUserId, gender, location, raasi, sort);

            request.setAttribute("profiles", profiles);
            request.setAttribute("gender", gender);
            request.setAttribute("location", location);
            request.setAttribute("raasi", raasi);
            request.setAttribute("sort", sort);

            request.getRequestDispatcher("/WEB-INF/views/user/discover.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load profiles. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/discover.jsp").forward(request, response);
        }
    }
}
