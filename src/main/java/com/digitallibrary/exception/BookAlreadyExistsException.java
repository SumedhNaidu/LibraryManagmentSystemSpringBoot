package com.digitallibrary.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String message) {
        super(message);
    }

    public BookAlreadyExistsException(String bookId, String additionalInfo) {
        super("Book with ID " + bookId + " already exists. " + additionalInfo);
    }
}