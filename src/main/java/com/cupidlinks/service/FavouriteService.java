package com.cupidlinks.service;

import com.cupidlinks.dao.FavouriteDAO;
import com.cupidlinks.model.Profile;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for favourite profile operations.
 * Controllers use this class to add, remove, and list saved profiles.
 */
public class FavouriteService {

    private final FavouriteDAO favouriteDAO = new FavouriteDAO();

    /**
     * Saves another user's profile as a favourite.
     *
     * @param userId ID of the user saving the profile
     * @param savedUserId ID of the profile owner being saved
     * @return true if the favourite was added
     * @throws SQLException if the database insert fails
     */
    public boolean addFavourite(int userId, int savedUserId) throws SQLException {
        return favouriteDAO.insert(userId, savedUserId);
    }

    /**
     * Removes a saved profile from a user's favourites.
     *
     * @param userId ID of the user removing the favourite
     * @param savedUserId ID of the saved profile owner
     * @return true if the favourite was removed
     * @throws SQLException if the database delete fails
     */
    public boolean removeFavourite(int userId, int savedUserId) throws SQLException {
        return favouriteDAO.delete(userId, savedUserId);
    }

    /**
     * Loads all favourite profiles for a user.
     *
     * @param userId ID of the user whose favourites are being loaded
     * @return list of saved Profile objects
     * @throws SQLException if the database query fails
     */
    public List<Profile> getFavourites(int userId) throws SQLException {
        return favouriteDAO.findByUserId(userId);
    }
}
