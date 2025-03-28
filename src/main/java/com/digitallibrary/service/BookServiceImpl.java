package com.digitallibrary.service;

import com.digitallibrary.exception.BookDeletionException;
import com.digitallibrary.exception.BookNotFoundException;
import com.digitallibrary.exception.InvalidBookDataException;
import com.digitallibrary.model.Book;
import com.digitallibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        if (bookRepository.existsByBookId(book.getBookId())) {
            throw new IllegalArgumentException("Book with ID " + book.getBookId() + " already exists");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(String bookId) {
        return bookRepository.findByBookId(bookId);
    }

    @Override
    public Book updateBook(String bookId, Book updatedBook) {
        Book existingBook = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        if (updatedBook.getTitle() != null) {
            existingBook.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            existingBook.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getGenre() != null) {
            existingBook.setGenre(updatedBook.getGenre());
        }
        existingBook.setAvailable(updatedBook.isAvailable());

        return bookRepository.save(existingBook);
    }

    @Override
    @Transactional
    public boolean deleteBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new InvalidBookDataException("Book ID cannot be null or empty");
        }

        if (!bookRepository.existsByBookId(bookId)) {
            throw new BookNotFoundException(bookId, "Cannot delete non-existent book");
        }

        int rowsAffected = bookRepository.deleteByBookId(bookId);

        if (rowsAffected == 0) {
            throw new BookDeletionException(bookId, "No rows were deleted");
        }
        return true;
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
}