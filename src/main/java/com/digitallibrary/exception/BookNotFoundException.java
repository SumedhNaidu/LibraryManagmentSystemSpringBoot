package com.digitallibrary.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(String bookId, String additionalInfo) {
        super("Book not found with ID: " + bookId + ". " + additionalInfo);
    }
}