package com.digitallibrary.exception;

public class InvalidBookDataException extends RuntimeException {
    public InvalidBookDataException(String message) {
        super(message);
    }

    public InvalidBookDataException(String field, String value) {
        super("Invalid book data: " + field + " = " + value);
    }
}