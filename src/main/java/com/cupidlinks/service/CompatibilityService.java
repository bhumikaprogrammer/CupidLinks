package com.cupidlinks.service;

import com.cupidlinks.dao.InterestDAO;
import com.cupidlinks.dao.ProfileDAO;
import com.cupidlinks.model.Profile;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Calculates a simple compatibility score from raasi pairing and shared interests.
 */
public class CompatibilityService {

    private final ProfileDAO profileDAO = new ProfileDAO();
    private final InterestDAO interestDAO = new InterestDAO();

    /**
     * Calculates a percentage-style compatibility score between two users.
     *
     * @param currentUserId ID of the logged-in user
     * @param otherUserId ID of the profile being scored
     * @return compatibility score between 0 and 100
     * @throws SQLException if profile or interest lookup fails
     */
    public int calculateScore(int currentUserId, int otherUserId) throws SQLException {
        Profile current = profileDAO.findByUserId(currentUserId);
        Profile other = profileDAO.findByUserId(otherUserId);

        int score = 25;
        score += calculateRaasiScore(current, other);
        score += calculateInterestScore(currentUserId, otherUserId);
        return Math.min(100, Math.max(0, score));
    }

    /**
     * Scores raasi compatibility using same-raasi and common friendly pairings.
     *
     * @param current logged-in user's profile
     * @param other profile being compared
     * @return raasi portion of the compatibility score
     */
    private int calculateRaasiScore(Profile current, Profile other) {
        if (current == null || other == null || current.getNepaliRaasi() == null || other.getNepaliRaasi() == null) {
            return 10;
        }
        if (current.getNepaliRaasi().equals(other.getNepaliRaasi())) {
            return 35;
        }

        String pair = current.getNepaliRaasi() + ":" + other.getNepaliRaasi();
        String reversePair = other.getNepaliRaasi() + ":" + current.getNepaliRaasi();
        Set<String> strongPairs = Set.of(
                "Mesh:Singh", "Mesh:Dhanu",
                "Brish:Kanya", "Brish:Makar",
                "Mithun:Tula", "Mithun:Kumbha",
                "Karkat:Brishchik", "Karkat:Meen",
                "Singh:Dhanu", "Kanya:Makar",
                "Tula:Kumbha", "Brishchik:Meen"
        );
        return strongPairs.contains(pair) || strongPairs.contains(reversePair) ? 30 : 18;
    }

    /**
     * Scores compatibility from shared interest tags.
     *
     * @param currentUserId ID of the logged-in user
     * @param otherUserId ID of the profile being compared
     * @return interest portion of the compatibility score
     * @throws SQLException if interest lookup fails
     */
    private int calculateInterestScore(int currentUserId, int otherUserId) throws SQLException {
        List<String> currentTags = interestDAO.findTagsByUserId(currentUserId);
        List<String> otherTags = interestDAO.findTagsByUserId(otherUserId);
        if (currentTags.isEmpty() || otherTags.isEmpty()) {
            return 5;
        }

        Set<String> shared = new HashSet<>(currentTags);
        shared.retainAll(new HashSet<>(otherTags));
        return Math.min(40, shared.size() * 10);
    }
}
