package com.adderbot.utils;

import com.adderbot.errors.Error;
import lombok.NonNull;

import java.util.Set;

public class ErrorUtils {
    public static String devHelpText = "Please contact the bot author for help.";

    public static String generateErrorMessages(@NonNull Set<Error> errors) {
        if (errors.size() == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Error error : errors) {
            stringBuilder.append("\n")
                    .append("Error: ")
                    .append(error.getMessage());
        }
        return stringBuilder.toString();
    }
}
