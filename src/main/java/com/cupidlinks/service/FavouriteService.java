package com.cupidlinks.service;

import com.cupidlinks.dao.FavouriteDAO;
import com.cupidlinks.model.Profile;

import java.sql.SQLException;
import java.util.List;

public class FavouriteService {

    private final FavouriteDAO favouriteDAO = new FavouriteDAO();

    public boolean addFavourite(int userId, int savedUserId) throws SQLException {
        return favouriteDAO.insert(userId, savedUserId);
    }

    public boolean removeFavourite(int userId, int savedUserId) throws SQLException {
        return favouriteDAO.delete(userId, savedUserId);
    }

    public List<Profile> getFavourites(int userId) throws SQLException {
        return favouriteDAO.findByUserId(userId);
    }
}