package com.adderbot.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    public static boolean isNullOrEmpty(Collection list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() == 0;
    }
}
