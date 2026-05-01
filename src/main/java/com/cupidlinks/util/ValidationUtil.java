package com.cupidlinks.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Common validation helper used by controllers before data is sent to the database.
 * Keeping these checks in one class avoids repeating the same validation rules in
 * registration, login, and profile management.
 */
public class ValidationUtil {

    private static final int MINIMUM_AGE = 18;

    /**
     * Checks whether a text value is missing or only contains spaces.
     *
     * @param value the text value submitted by the user
     * @return true if the value is null or blank, otherwise false
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates a person's name. Only letters and spaces are accepted so that
     * values like numbers or symbols are not stored as a full name.
     *
     * @param name the full name entered by the user
     * @return true if the name contains only letters and spaces
     */
    public static boolean isValidName(String name) {
        if (isEmpty(name)) return false;
        return name.trim().matches("[a-zA-Z\\s]+");
    }

    /**
     * Checks whether an email follows a normal email format.
     *
     * @param email the email address entered by the user
     * @return true if the email format is valid
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        return email.trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Validates Nepali mobile numbers used by the system.
     *
     * @param phone the phone number entered by the user
     * @return true if the phone starts with 97 or 98 and has 10 digits
     */
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) return false;
        return phone.trim().matches("^(97|98)\\d{8}$");
    }

    /**
     * Enforces a stronger password rule for account security.
     *
     * @param password the plain password entered during registration
     * @return true if the password has uppercase, lowercase, number, special character, and minimum length
     */
    public static boolean isValidPassword(String password) {
        if (isEmpty(password)) return false;
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    /**
     * Checks the age requirement for CupidLinks. Since this is a dating platform,
     * users must be adults before they can create an account.
     *
     * @param dob the user's date of birth
     * @return true if the user is at least 18 years old
     */
    public static boolean isAdult(LocalDate dob) {
        if (dob == null) return false;
        return Period.between(dob, LocalDate.now()).getYears() >= MINIMUM_AGE;
    }

    /**
     * Compares password and confirm-password fields.
     *
     * @param password the original password
     * @param confirmPassword the repeated password confirmation
     * @return true if both password fields match
     */
    public static boolean passwordsMatch(String password, String confirmPassword) {
        if (isEmpty(password) || isEmpty(confirmPassword)) return false;
        return password.equals(confirmPassword);
    }

    /**
     * Trims input safely and converts null values into an empty string.
     *
     * @param input raw form input from the request
     * @return trimmed input, or an empty string when input is null
     */
    public static String sanitize(String input) {
        return input == null ? "" : input.trim();
    }
}
