package com.cupidlinks.controller;

import com.cupidlinks.model.Match;
import com.cupidlinks.service.MatchService;
import com.cupidlinks.service.MessageService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.List;
import java.util.Map;

/**
 * Controller for match-only messaging.
 */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

    private final MatchService matchService = new MatchService();
    private final MessageService messageService = new MessageService();
    private static final Set<String> ICE_BREAKERS = Set.of(
            "Tea or coffee?",
            "Pizza or momo?",
            "Morning or night?",
            "Cats or dogs?",
            "Call or text?"
    );
    private static final Set<String> QUICK_CHOICES = Set.of(
            "Tea",
            "Coffee",
            "Pizza",
            "Momo",
            "Morning",
            "Night",
            "Cats",
            "Dogs",
            "Call",
            "Text"
    );
    private static final Set<String> SOCIAL_PLATFORMS = Set.of("Instagram", "Facebook", "WhatsApp", "TikTok", "LinkedIn");

    /**
     * Loads the user's matched profiles and the selected match conversation.
     *
     * @param request HTTP request containing optional matchId query parameter
     * @param response HTTP response used to forward to the messages JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String matchIdParam = ValidationUtil.sanitize(request.getParameter("matchId"));

        try {
            List<Map<String, Object>> matchData = matchService.getMatchDataForUser(userId);
            request.setAttribute("matchData", matchData);

            int selectedMatchId = resolveSelectedMatchId(matchIdParam, matchData);
            if (selectedMatchId > 0) {
                Match match = messageService.getMatchForUser(selectedMatchId, userId);
                if (match != null) {
                    request.setAttribute("selectedMatchId", selectedMatchId);
                    request.setAttribute("messages", messageService.getConversation(selectedMatchId, userId));
                }
            }

            request.getRequestDispatcher("/WEB-INF/views/user/messages.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Could not load messages. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/messages.jsp").forward(request, response);
        }
    }

    /**
     * Sends a message inside a match conversation after confirming the sender belongs to that match.
     *
     * @param request HTTP request containing matchId and message content
     * @param response HTTP response used to redirect back to the conversation
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String matchIdParam = ValidationUtil.sanitize(request.getParameter("matchId"));
        String action = ValidationUtil.sanitize(request.getParameter("action"));

        try {
            int matchId = Integer.parseInt(matchIdParam);
            String content = buildMessageContent(request, action);
            boolean sent = messageService.sendMessage(matchId, userId, content);
            response.sendRedirect(request.getContextPath() + "/messages?matchId=" + matchId + (sent ? "&sent=true" : "&error=true"));
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/messages?error=true");
        }
    }

    /**
     * Builds the message body from an approved ice breaker, preset reply, or social account share.
     *
     * @param request HTTP request containing form fields
     * @param action selected message action
     * @return message content to save
     */
    private String buildMessageContent(HttpServletRequest request, String action) {
        if ("quickChoice".equals(action)) {
            String choice = ValidationUtil.sanitize(request.getParameter("choice"));
            if (!QUICK_CHOICES.contains(choice)) {
                return "";
            }
            return choice;
        }

        if ("shareSocial".equals(action)) {
            String platform = ValidationUtil.sanitize(request.getParameter("platform"));
            String handle = ValidationUtil.sanitize(request.getParameter("handle"));
            if (!SOCIAL_PLATFORMS.contains(platform) || ValidationUtil.isEmpty(handle)) {
                return "";
            }
            return "Shared social account - " + platform + ": " + handle;
        }

        String iceBreaker = ValidationUtil.sanitize(request.getParameter("iceBreaker"));
        if (!ICE_BREAKERS.contains(iceBreaker)) {
            return "";
        }
        return "Ice breaker: " + iceBreaker;
    }

    /**
     * Chooses the requested match when present, otherwise falls back to the first available match.
     *
     * @param matchIdParam optional match ID from the query string
     * @param matchData matched profile data loaded for the current user
     * @return selected match ID, or 0 when no valid match can be selected
     */
    private int resolveSelectedMatchId(String matchIdParam, List<Map<String, Object>> matchData) {
        if (!ValidationUtil.isEmpty(matchIdParam)) {
            try {
                return Integer.parseInt(matchIdParam);
            } catch (NumberFormatException ignored) {
                return 0;
            }
        }
        if (matchData.isEmpty()) {
            return 0;
        }
        Match firstMatch = (Match) matchData.get(0).get("match");
        return firstMatch.getMatchId();
    }
}
