package com.cupidlinks.controller;

import com.cupidlinks.model.Profile;
import com.cupidlinks.service.ProfileService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Handles profile viewing and profile updates for logged-in users.
 * This servlet also manages profile photo upload, which is stored outside
 * the deployed webapp so uploaded files are not lost during redeployment.
 */
@WebServlet("/profile")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class ProfileServlet extends HttpServlet {

    private final ProfileService profileService = new ProfileService();

    /**
     * Creates or returns the local folder used to store uploaded profile photos.
     *
     * @return absolute upload directory path
     */
    private String getUploadDir() {
        String base = System.getProperty("user.home");
        String dir = base + File.separator + "cupidlinks_uploads";
        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();
        return dir;
    }

    /**
     * Loads the current user's profile for editing.
     *
     * @param request HTTP request containing the active session
     * @param response HTTP response used to forward to the profile JSP
     * @throws ServletException if forwarding fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        try {
            Profile profile = profileService.getProfileByUserId(userId);
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Could not load profile. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        }
    }

    /**
     * Validates and saves the profile form, including optional photo upload.
     *
     * @param request multipart HTTP request containing profile fields and photo
     * @param response HTTP response used for redirecting or forwarding
     * @throws ServletException if multipart parsing or forwarding fails
     * @throws IOException if file saving or response writing fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String fullName         = ValidationUtil.sanitize(request.getParameter("fullName"));
        String dobStr           = ValidationUtil.sanitize(request.getParameter("dateOfBirth"));
        String gender           = ValidationUtil.sanitize(request.getParameter("gender"));
        String bio              = ValidationUtil.sanitize(request.getParameter("bio"));
        String location         = ValidationUtil.sanitize(request.getParameter("location"));
        String datingPreference = ValidationUtil.sanitize(request.getParameter("datingPreference"));
        String nepaliRaasi      = ValidationUtil.sanitize(request.getParameter("nepaliRaasi"));
        String clan             = ValidationUtil.sanitize(request.getParameter("clan"));
        String visibility       = ValidationUtil.sanitize(request.getParameter("visibility"));

        if (ValidationUtil.isEmpty(fullName) || ValidationUtil.isEmpty(dobStr) || ValidationUtil.isEmpty(gender)) {
            request.setAttribute("error", "Full name, date of birth and gender are required.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidName(fullName)) {
            request.setAttribute("error", "Full name can only contain letters and spaces.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid date of birth.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isAdult(dob)) {
            request.setAttribute("error", "You must be at least 18 years old.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            return;
        }

        String photoFileName = null;
        try {
            Part photoPart = request.getPart("profilePhoto");
            if (photoPart != null && photoPart.getSize() > 0) {
                String originalName = photoPart.getSubmittedFileName();
                String ext = originalName.substring(originalName.lastIndexOf("."));
                // Use a random name so two users uploading the same filename do not overwrite each other.
                photoFileName = UUID.randomUUID().toString() + ext;
                String uploadDir = getUploadDir();
                try (InputStream input = photoPart.getInputStream()) {
                    Files.copy(input, Paths.get(uploadDir, photoFileName));
                }
            }
        } catch (Exception e) {
            System.err.println("Photo upload failed: " + e.getMessage());
        }

        try {
            Profile existing = profileService.getProfileByUserId(userId);
            Profile profile = new Profile();
            profile.setUserId(userId);
            profile.setFullName(fullName);
            profile.setDateOfBirth(dob);
            profile.setGender(gender);
            profile.setBio(bio);
            profile.setLocation(location);
            profile.setDatingPreference(datingPreference);
            profile.setNepaliRaasi(nepaliRaasi.isEmpty() ? null : nepaliRaasi);
            profile.setClan(clan.isEmpty() ? null : clan);
            profile.setVisibility(visibility.isEmpty() ? "public" : visibility);

            if (photoFileName != null) {
                profile.setProfilePhoto(photoFileName);
            } else if (existing != null) {
                profile.setProfilePhoto(existing.getProfilePhoto());
            }

            boolean success = (existing == null)
                    ? profileService.createProfile(profile)
                    : profileService.updateProfile(profile);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/profile?saved=true");
            } else {
                request.setAttribute("error", "Could not save profile. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("error", "A database error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        }
    }
}
