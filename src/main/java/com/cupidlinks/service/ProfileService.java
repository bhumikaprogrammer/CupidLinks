package com.cupidlinks.service;

import com.cupidlinks.dao.ProfileDAO;
import com.cupidlinks.model.Profile;

import java.sql.SQLException;
import java.util.List;

public class ProfileService {

    private final ProfileDAO profileDAO = new ProfileDAO();

    public List<Profile> discoverProfiles(int currentUserId, String gender, String location, String raasi, String sort) throws SQLException {
        return profileDAO.findForDiscover(currentUserId, gender, location, raasi, sort);
    }

    public Profile getProfileByUserId(int userId) throws SQLException {
        return profileDAO.findByUserId(userId);
    }

    public boolean createProfile(Profile profile) throws SQLException {
        return profileDAO.insert(profile);
    }

    public boolean updateProfile(Profile profile) throws SQLException {
        return profileDAO.update(profile);
    }
}