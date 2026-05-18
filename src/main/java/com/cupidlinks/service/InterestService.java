package com.cupidlinks.service;

import com.cupidlinks.dao.InterestDAO;
import com.cupidlinks.dao.MatchDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for user interest actions.
 * It creates matches when two users have accepted interest in each other.
 */
public class InterestService {

    private final InterestDAO interestDAO = new InterestDAO();
    private final MatchDAO    matchDAO    = new MatchDAO();

    /**
     * Sends interest from one user to another and creates a match when interest is mutual.
     *
     * @param senderId ID of the user sending interest
     * @param receiverId ID of the user receiving interest
     * @return true if the action created a new mutual match
     * @throws SQLException if inserting interest or match data fails
     */
    public boolean sendLike(int senderId, int receiverId) throws SQLException {
        interestDAO.insert(senderId, receiverId);
        if (interestDAO.mutualExists(senderId, receiverId)) {
            matchDAO.insert(senderId, receiverId);
            return true;
        }
        return false;
    }

    /**
     * Loads profile interest tags for a user.
     *
     * @param userId ID of the user whose tags are being loaded
     * @return list of interest tags
     * @throws SQLException if the database query fails
     */
    public List<String> getUserInterestTags(int userId) throws SQLException {
        return interestDAO.findTagsByUserId(userId);
    }

    /**
     * Saves the selected profile interest tags for a user.
     *
     * @param userId ID of the user whose tags are being saved
     * @param tags selected interest tags
     * @throws SQLException if updating tags fails
     */
    public void updateUserInterestTags(int userId, List<String> tags) throws SQLException {
        interestDAO.replaceTags(userId, tags);
    }
}
