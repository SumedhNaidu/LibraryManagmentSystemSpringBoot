package com.digitallibrary.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AvailabilityStatus {
    AVAILABLE, CHECKED_OUT;

    @JsonCreator
    public static AvailabilityStatus fromString(String value) {
        return switch (value.toUpperCase()) {
            case "AVAILABLE" -> AVAILABLE;
            case "CHECKED_OUT" -> CHECKED_OUT;
            default -> throw new IllegalArgumentException("Invalid status: " + value);
        };
    }
}
