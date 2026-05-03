package com.cupidlinks.controller;

import com.cupidlinks.dao.MatchDAO;
import com.cupidlinks.dao.ProfileDAO;
import com.cupidlinks.model.Match;
import com.cupidlinks.model.Profile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for showing the logged-in user's matches.
 */
@WebServlet("/matches")
public class MatchServlet extends HttpServlet {

    private final MatchDAO   matchDAO   = new MatchDAO();
    private final ProfileDAO profileDAO = new ProfileDAO();

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
            List<Match> matches = matchDAO.findByUserId(userId);

            // for each match load the other person's profile
            List<Map<String, Object>> matchData = new ArrayList<>();
            for (Match match : matches) {
                int otherUserId = (match.getUser1Id() == userId) ? match.getUser2Id() : match.getUser1Id();
                Profile otherProfile = profileDAO.findByUserId(otherUserId);

                Map<String, Object> entry = new HashMap<>();
                entry.put("match", match);
                entry.put("profile", otherProfile);
                matchData.add(entry);
            }

            request.setAttribute("matchData", matchData);
            request.getRequestDispatcher("/WEB-INF/views/user/matches.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Could not load matches. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/matches.jsp").forward(request, response);
        }
    }
}
