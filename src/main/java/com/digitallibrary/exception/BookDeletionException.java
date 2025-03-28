package com.digitallibrary.exception;

public class BookDeletionException extends RuntimeException {
    public BookDeletionException(String message) {
        super(message);
    }

    public BookDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookDeletionException(String bookId, String reason) {
        super("Failed to delete book with ID: " + bookId + ". Reason: " + reason);
    }
}