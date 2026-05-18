package com.cupidlinks.dao;

import com.cupidlinks.model.Profile;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for the favourites table.
 * It manages saved user profiles for each account.
 */
public class FavouriteDAO {

    /**
     * Saves another user's profile as a favourite for the current user.
     *
     * @param userId ID of the user saving the favourite
     * @param savedUserId ID of the user profile being saved
     * @return true if the favourite was inserted successfully
     * @throws SQLException if the insert query fails
     */
    public boolean insert(int userId, int savedUserId) throws SQLException {
        String sql = "INSERT IGNORE INTO favourites (user_id, saved_user_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, savedUserId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Removes a saved favourite profile for the current user.
     *
     * @param userId ID of the user removing the favourite
     * @param savedUserId ID of the saved user profile to remove
     * @return true if the favourite was deleted
     * @throws SQLException if the delete query fails
     */
    public boolean delete(int userId, int savedUserId) throws SQLException {
        String sql = "DELETE FROM favourites WHERE user_id = ? AND saved_user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, savedUserId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Loads all profile records saved as favourites by a user.
     *
     * @param userId ID of the user whose favourites are being loaded
     * @return list of saved Profile objects
     * @throws SQLException if the select query fails
     */
    public List<Profile> findByUserId(int userId) throws SQLException {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT p.* FROM profiles p JOIN favourites f ON p.user_id = f.saved_user_id WHERE f.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Profile p = new Profile();
                p.setProfileId(rs.getInt("profile_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setFullName(rs.getString("full_name"));
                p.setDateOfBirth(rs.getObject("date_of_birth", LocalDate.class));
                p.setGender(rs.getString("gender"));
                p.setBio(rs.getString("bio"));
                p.setLocation(rs.getString("location"));
                p.setNepaliRaasi(rs.getString("nepali_raasi"));
                p.setClan(rs.getString("clan"));
                p.setProfilePhoto(rs.getString("profile_photo"));
                profiles.add(p);
            }
        }
        return profiles;
    }
}
