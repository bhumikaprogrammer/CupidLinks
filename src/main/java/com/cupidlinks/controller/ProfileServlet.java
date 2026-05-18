package com.cupidlinks.controller;

import com.cupidlinks.model.Profile;
import com.cupidlinks.service.InterestService;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Handles profile viewing and profile updates for logged-in users.
 * This servlet also manages profile photo upload.
 */
@WebServlet("/profile")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class ProfileServlet extends HttpServlet {

    private final ProfileService profileService = new ProfileService();
    private final InterestService interestService = new InterestService();
    private static final List<String> ALLOWED_INTERESTS = Arrays.asList(
            "Music", "Travel", "Food", "Movies", "Books", "Fitness",
            "Hiking", "Photography", "Dancing", "Gaming", "Art", "Spirituality"
    );
    private static final List<String> ALLOWED_DATING_PREFERENCES = Arrays.asList(
            "open", "casual", "serious", "friendship"
    );

    /**
     * Creates or returns the local folder used to store uploaded profile photos.
     *
     * @return absolute upload directory path
     */
    private Path getUploadDir() throws IOException {
        Path uploadDir = findSourceUploadDir();
        Files.createDirectories(uploadDir);
        return uploadDir;
    }

    private Path findSourceUploadDir() {
        Path fromWorkingDir = findProjectUploadDir(Paths.get(System.getProperty("user.dir")));
        if (fromWorkingDir != null) {
            return fromWorkingDir;
        }

        String deployedRoot = getServletContext().getRealPath("/");
        if (deployedRoot != null) {
            Path fromDeployment = findProjectUploadDir(Paths.get(deployedRoot));
            if (fromDeployment != null) {
                return fromDeployment;
            }
        }

        return Paths.get(getServletContext().getRealPath("/uploads")).toAbsolutePath().normalize();
    }

    private Path findProjectUploadDir(Path start) {
        Path current = start.toAbsolutePath().normalize();
        while (current != null) {
            Path webappDir = current.resolve(Paths.get("src", "main", "webapp"));
            if (Files.isDirectory(webappDir)) {
                return webappDir.resolve("uploads").toAbsolutePath().normalize();
            }
            current = current.getParent();
        }
        return null;
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
            request.setAttribute("selectedInterests", interestService.getUserInterestTags(userId));
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Could not load profile. Please try again.");
            request.setAttribute("selectedInterests", List.of());
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
        List<String> selectedInterests = sanitizeInterests(request.getParameterValues("interests"));
        request.setAttribute("selectedInterests", selectedInterests);

        if (ValidationUtil.isEmpty(fullName) || ValidationUtil.isEmpty(dobStr) || ValidationUtil.isEmpty(gender)
                || ValidationUtil.isEmpty(datingPreference)) {
            request.setAttribute("error", "Full name, date of birth, gender and looking for are required.");
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            return;
        }

        if (!ALLOWED_DATING_PREFERENCES.contains(datingPreference)) {
            request.setAttribute("error", "Please choose a valid looking for option.");
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
                Path uploadDir = getUploadDir();
                try (InputStream input = photoPart.getInputStream()) {
                    Files.copy(input, uploadDir.resolve(photoFileName));
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
                interestService.updateUserInterestTags(userId, selectedInterests);
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

    /**
     * Keeps only known interest tags from the submitted checkbox values.
     *
     * @param values raw submitted interest values
     * @return validated interest tag list
     */
    private List<String> sanitizeInterests(String[] values) {
        List<String> tags = new ArrayList<>();
        if (values == null) {
            return tags;
        }

        for (String value : values) {
            String tag = ValidationUtil.sanitize(value);
            if (ALLOWED_INTERESTS.contains(tag) && !tags.contains(tag)) {
                tags.add(tag);
            }
        }
        return tags;
    }
}
