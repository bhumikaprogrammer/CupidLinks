package com.cupidlinks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model class representing profile details for a user account.
 */
public class Profile {

    private int profileId;
    private int userId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String bio;
    private String location;
    private String datingPreference;
    private String profilePhoto;
    private String nepaliRaasi;
    private String clan;
    private String visibility;
    private int compatibilityScore;
    private LocalDateTime updatedAt;

    /**
     * Creates an empty Profile object.
     */
    public Profile() {}

    /**
     * @return unique profile ID
     */
    public int getProfileId() { return profileId; }

    /**
     * @param profileId unique profile ID
     */
    public void setProfileId(int profileId) { this.profileId = profileId; }

    /**
     * @return ID of the user who owns this profile
     */
    public int getUserId() { return userId; }

    /**
     * @param userId ID of the user who owns this profile
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * @return user's full name
     */
    public String getFullName() { return fullName; }

    /**
     * @param fullName user's full name
     */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /**
     * @return user's date of birth
     */
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    /**
     * @param dateOfBirth user's date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    /**
     * @return user's gender
     */
    public String getGender() { return gender; }

    /**
     * @param gender user's gender
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * @return profile biography text
     */
    public String getBio() { return bio; }

    /**
     * @param bio profile biography text
     */
    public void setBio(String bio) { this.bio = bio; }

    /**
     * @return user's location
     */
    public String getLocation() { return location; }

    /**
     * @param location user's location
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * @return user's dating preference
     */
    public String getDatingPreference() { return datingPreference; }

    /**
     * @param datingPreference user's dating preference
     */
    public void setDatingPreference(String datingPreference) { this.datingPreference = datingPreference; }

    /**
     * @return profile photo path or URL
     */
    public String getProfilePhoto() { return profilePhoto; }

    /**
     * @param profilePhoto profile photo path or URL
     */
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }

    /**
     * @return user's Nepali raasi
     */
    public String getNepaliRaasi() { return nepaliRaasi; }

    /**
     * @param nepaliRaasi user's Nepali raasi
     */
    public void setNepaliRaasi(String nepaliRaasi) { this.nepaliRaasi = nepaliRaasi; }

    /**
     * @return user's clan
     */
    public String getClan() { return clan; }

    /**
     * @param clan user's clan
     */
    public void setClan(String clan) { this.clan = clan; }

    /**
     * @return profile visibility setting
     */
    public String getVisibility() { return visibility; }

    /**
     * @param visibility profile visibility setting
     */
    public void setVisibility(String visibility) { this.visibility = visibility; }

    /**
     * @return compatibility score shown on discovery cards
     */
    public int getCompatibilityScore() { return compatibilityScore; }

    /**
     * @param compatibilityScore compatibility score shown on discovery cards
     */
    public void setCompatibilityScore(int compatibilityScore) { this.compatibilityScore = compatibilityScore; }

    /**
     * @return date and time when the profile was last updated
     */
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /**
     * @param updatedAt date and time when the profile was last updated
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
