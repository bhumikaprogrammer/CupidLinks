package com.cupidlinks.service;

import com.cupidlinks.dao.MatchDAO;
import com.cupidlinks.model.Match;

import java.sql.SQLException;
import java.util.List;

public class MatchService {

    private final MatchDAO matchDAO = new MatchDAO();

    public List<Match> getMatchesForUser(int userId) throws SQLException {
        return matchDAO.findByUserId(userId);
    }
}