package com.digitallibrary.service;

import com.digitallibrary.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Optional<Book> getBookById(String bookId);
    Book updateBook(String bookId, Book updatedBook);
    boolean deleteBook(String bookId);
    List<Book> searchBooksByTitle(String title);
    List<Book> getAvailableBooks();
    List<Book> getBooksByGenre(String genre);
}