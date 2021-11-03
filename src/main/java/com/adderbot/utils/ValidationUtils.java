package com.adderbot.utils;

/**
 * Collection of utilities for validators
 */
public class ValidationUtils {
    /**
     * Checks if a string is null, empty, or containing only white space
     * @param str The string to test
     * @return True if string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }
}
