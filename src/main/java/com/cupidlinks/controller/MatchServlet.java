package com.cupidlinks.controller;

import com.cupidlinks.service.MatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for showing the logged-in user's matches.
 */
@WebServlet("/matches")
public class MatchServlet extends HttpServlet {

    private final MatchService matchService = new MatchService();

    /**
     * Loads match records and the other user's profile for each match.
     *
     * @param request HTTP request containing the active session
     * @param response HTTP response used to forward to the matches JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        try {
            request.setAttribute("matchData", matchService.getMatchDataForUser(userId));
            request.getRequestDispatcher("/WEB-INF/views/user/matches.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Could not load matches. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/matches.jsp").forward(request, response);
        }
    }
}
