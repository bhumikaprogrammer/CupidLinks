package com.cupidlinks.dao;

import com.cupidlinks.model.Profile;
import com.cupidlinks.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO {

    public Profile findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM profiles WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRow(rs);
        }
        return null;
    }

    public List<Profile> findForDiscover(int currentUserId, String gender, String location, String raasi, String sort) throws SQLException {
        List<Profile> profiles = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT p.* FROM profiles p " +
                        "JOIN users u ON p.user_id = u.user_id " +
                        "WHERE p.user_id != ? AND u.status = 'approved' AND p.visibility = 'public'"
        );
        if (gender != null && !gender.isEmpty())   sql.append(" AND p.gender = ?");
        if (location != null && !location.isEmpty()) sql.append(" AND p.location LIKE ?");
        if (raasi != null && !raasi.isEmpty())     sql.append(" AND p.nepali_raasi = ?");
        sql.append("oldest".equals(sort) ? " ORDER BY p.profile_id ASC" : " ORDER BY p.profile_id DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int i = 1;
            stmt.setInt(i++, currentUserId);
            if (gender != null && !gender.isEmpty())   stmt.setString(i++, gender);
            if (location != null && !location.isEmpty()) stmt.setString(i++, "%" + location + "%");
            if (raasi != null && !raasi.isEmpty())     stmt.setString(i++, raasi);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) profiles.add(mapRow(rs));
        }
        return profiles;
    }

    public boolean insert(Profile p) throws SQLException {
        String sql = "INSERT INTO profiles (user_id, full_name, date_of_birth, gender, bio, location, dating_preference, nepali_raasi, clan, visibility) VALUES (?,?,?,?,?,?,?,?,?,'public')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getUserId());
            stmt.setString(2, p.getFullName());
            stmt.setObject(3, p.getDateOfBirth());
            stmt.setString(4, p.getGender());
            stmt.setString(5, p.getBio());
            stmt.setString(6, p.getLocation());
            stmt.setString(7, p.getDatingPreference());
            stmt.setString(8, p.getNepaliRaasi());
            stmt.setString(9, p.getClan());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Profile p) throws SQLException {
        String sql = "UPDATE profiles SET full_name=?, date_of_birth=?, gender=?, bio=?, location=?, dating_preference=?, nepali_raasi=?, clan=?, visibility=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getFullName());
            stmt.setObject(2, p.getDateOfBirth());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getBio());
            stmt.setString(5, p.getLocation());
            stmt.setString(6, p.getDatingPreference());
            stmt.setString(7, p.getNepaliRaasi());
            stmt.setString(8, p.getClan());
            stmt.setString(9, p.getVisibility());
            stmt.setInt(10, p.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    private Profile mapRow(ResultSet rs) throws SQLException {
        Profile p = new Profile();
        p.setProfileId(rs.getInt("profile_id"));
        p.setUserId(rs.getInt("user_id"));
        p.setFullName(rs.getString("full_name"));
        p.setDateOfBirth(rs.getObject("date_of_birth", LocalDate.class));
        p.setGender(rs.getString("gender"));
        p.setBio(rs.getString("bio"));
        p.setLocation(rs.getString("location"));
        p.setDatingPreference(rs.getString("dating_preference"));
        p.setNepaliRaasi(rs.getString("nepali_raasi"));
        p.setClan(rs.getString("clan"));
        p.setVisibility(rs.getString("visibility"));
        p.setProfilePhoto(rs.getString("profile_photo"));
        return p;
    }
}
