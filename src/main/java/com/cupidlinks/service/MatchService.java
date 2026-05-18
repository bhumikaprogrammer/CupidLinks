package com.cupidlinks.service;

import com.cupidlinks.dao.MatchDAO;
import com.cupidlinks.dao.ProfileDAO;
import com.cupidlinks.model.Match;
import com.cupidlinks.model.Profile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer for match lookup operations.
 */
public class MatchService {

    private final MatchDAO matchDAO = new MatchDAO();
    private final ProfileDAO profileDAO = new ProfileDAO();

    /**
     * Loads all matches for a specific user.
     *
     * @param userId ID of the user whose matches are being loaded
     * @return list of Match objects involving the user
     * @throws SQLException if the database query fails
     */
    public List<Match> getMatchesForUser(int userId) throws SQLException {
        return matchDAO.findByUserId(userId);
    }

    public List<Map<String, Object>> getMatchDataForUser(int userId) throws SQLException {
        List<Match> matches = matchDAO.findByUserId(userId);
        List<Map<String, Object>> matchData = new ArrayList<>();

        for (Match match : matches) {
            int otherUserId = (match.getUser1Id() == userId) ? match.getUser2Id() : match.getUser1Id();
            Profile otherProfile = profileDAO.findByUserId(otherUserId);

            Map<String, Object> entry = new HashMap<>();
            entry.put("match", match);
            entry.put("profile", otherProfile);
            entry.put("otherUserId", otherUserId);
            matchData.add(entry);
        }

        return matchData;
    }
}
