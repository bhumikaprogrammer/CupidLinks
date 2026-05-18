package com.cupidlinks.service;

import com.cupidlinks.dao.ProfileDAO;
import com.cupidlinks.model.Profile;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for profile management and discovery features.
 */
public class ProfileService {

    private final ProfileDAO profileDAO = new ProfileDAO();

    /**
     * Loads discoverable profiles using optional filters.
     *
     * @param currentUserId ID of the logged-in user to exclude from results
     * @param gender optional gender filter
     * @param location optional location search text
     * @param raasi optional Nepali raasi filter
     * @param sort selected sort option
     * @return list of matching Profile objects
     * @throws SQLException if the database query fails
     */
    public List<Profile> discoverProfiles(int currentUserId, String gender, String location, String raasi, String sort) throws SQLException {
        return profileDAO.findForDiscover(currentUserId, gender, location, raasi, sort);
    }

    /**
     * Loads a profile by user ID.
     *
     * @param userId ID of the profile owner
     * @return matching Profile object, or null if no profile exists
     * @throws SQLException if the database query fails
     */
    public Profile getProfileByUserId(int userId) throws SQLException {
        return profileDAO.findByUserId(userId);
    }

    /**
     * Creates a new profile.
     *
     * @param profile profile data to save
     * @return true if the profile was created
     * @throws SQLException if the database insert fails
     */
    public boolean createProfile(Profile profile) throws SQLException {
        return profileDAO.insert(profile);
    }

    /**
     * Updates an existing profile.
     *
     * @param profile profile data containing updated values
     * @return true if the profile was updated
     * @throws SQLException if the database update fails
     */
    public boolean updateProfile(Profile profile) throws SQLException {
        return profileDAO.update(profile);
    }
}
