package com.cupidlinks.controller;

import com.cupidlinks.model.Profile;
import com.cupidlinks.service.FavouriteService;
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
 * Controller for viewing and updating a user's favourite profiles.
 */
@WebServlet("/favourites")
public class FavouriteServlet extends HttpServlet {

    private final FavouriteService favouriteService = new FavouriteService();

    /**
     * Loads all profiles saved as favourites by the logged-in user.
     *
     * @param request HTTP request containing the active session
     * @param response HTTP response used to forward to the favourites JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        try {
            List<Profile> favourites = favouriteService.getFavourites(userId);
            request.setAttribute("favourites", favourites);
            request.getRequestDispatcher("/WEB-INF/views/user/favourites.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Could not load favourites.");
            request.getRequestDispatcher("/WEB-INF/views/user/favourites.jsp").forward(request, response);
        }
    }

    /**
     * Adds or removes a favourite profile for the logged-in user.
     *
     * @param request HTTP request containing savedUserId and action values
     * @param response HTTP response used to redirect back to favourites
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String savedUserIdStr = ValidationUtil.sanitize(request.getParameter("savedUserId"));
        String action         = ValidationUtil.sanitize(request.getParameter("action"));

        try {
            int savedUserId = Integer.parseInt(savedUserIdStr);

            if ("remove".equals(action)) {
                favouriteService.removeFavourite(userId, savedUserId);
            } else {
                favouriteService.addFavourite(userId, savedUserId);
            }

            response.sendRedirect(request.getContextPath() + "/favourites");

        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/favourites?error=true");
        }
    }
}
