package com.cupidlinks.service;

import com.cupidlinks.dao.MatchDAO;
import com.cupidlinks.dao.MessageDAO;
import com.cupidlinks.model.Match;
import com.cupidlinks.model.Message;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for match-only conversations.
 */
public class MessageService {

    private final MatchDAO matchDAO = new MatchDAO();
    private final MessageDAO messageDAO = new MessageDAO();

    /**
     * Loads a match only when the supplied user belongs to it.
     *
     * @param matchId ID of the match conversation
     * @param userId ID of the current user
     * @return matching Match object, or null when the user is not part of the match
     * @throws SQLException if the database query fails
     */
    public Match getMatchForUser(int matchId, int userId) throws SQLException {
        return matchDAO.findByIdForUser(matchId, userId);
    }

    /**
     * Loads messages for a match after confirming the current user belongs to that match.
     *
     * @param matchId ID of the match conversation
     * @param userId ID of the current user
     * @return list of conversation messages, or an empty list if unauthorized
     * @throws SQLException if the database query fails
     */
    public List<Message> getConversation(int matchId, int userId) throws SQLException {
        Match match = matchDAO.findByIdForUser(matchId, userId);
        if (match == null) {
            return List.of();
        }
        return messageDAO.findByMatchId(matchId);
    }

    /**
     * Sends a message only when the sender is part of the selected match.
     *
     * @param matchId ID of the match conversation
     * @param senderId ID of the user sending the message
     * @param content message body text
     * @return true if the message was saved
     * @throws SQLException if the database insert fails
     */
    public boolean sendMessage(int matchId, int senderId, String content) throws SQLException {
        Match match = matchDAO.findByIdForUser(matchId, senderId);
        if (match == null || content == null || content.trim().isEmpty()) {
            return false;
        }
        return messageDAO.insert(matchId, senderId, content.trim());
    }
}
