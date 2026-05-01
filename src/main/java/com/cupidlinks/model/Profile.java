package com.cupidlinks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime updatedAt;

    public Profile() {}

    public int getProfileId() { return profileId; }
    public void setProfileId(int profileId) { this.profileId = profileId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDatingPreference() { return datingPreference; }
    public void setDatingPreference(String datingPreference) { this.datingPreference = datingPreference; }

    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }

    public String getNepaliRaasi() { return nepaliRaasi; }
    public void setNepaliRaasi(String nepaliRaasi) { this.nepaliRaasi = nepaliRaasi; }

    public String getClan() { return clan; }
    public void setClan(String clan) { this.clan = clan; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
