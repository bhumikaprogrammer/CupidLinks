package com.cupidlinks.service;

import com.cupidlinks.dao.InterestDAO;
import com.cupidlinks.dao.MatchDAO;

import java.sql.SQLException;

public class InterestService {

    private final InterestDAO interestDAO = new InterestDAO();
    private final MatchDAO    matchDAO    = new MatchDAO();

    public boolean sendLike(int senderId, int receiverId) throws SQLException {
        interestDAO.insert(senderId, receiverId);
        if (interestDAO.mutualExists(senderId, receiverId)) {
            matchDAO.insert(senderId, receiverId);
            return true;
        }
        return false;
    }
}