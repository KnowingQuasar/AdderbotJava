package com.adderbot.errors;

import lombok.Data;
import lombok.NonNull;

@Data
public class Error {
    @NonNull
    private String message;
}
