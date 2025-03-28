package com.digitallibrary.controller;

import com.digitallibrary.model.Book;
import com.digitallibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Adds a new book to the system.
     * @param book The book object containing title, author, etc.
     * @return HTTP 201 Created with the added book details.
     * Example Response:
     * {
     *   "message": "Book added successfully",
     *   "book": { "id": "B001", "title": "Spring Boot in Action", "author": "Craig Walls" }
     * }
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addBook(@Valid @RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book added successfully");
        response.put("book", addedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all books from the system.
     * @return HTTP 200 OK with a list of books.
     * Example Response:
     * {
     *   "count": 2,
     *   "books": [
     *     { "id": "B001", "title": "Spring Boot in Action", "author": "Craig Walls" },
     *     { "id": "B002", "title": "Effective Java", "author": "Joshua Bloch" }
     *   ]
     * }
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        Map<String, Object> response = new HashMap<>();
        response.put("count", books.size());
        response.put("books", books);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a book by its ID.
     * @param bookId The unique ID of the book.
     * @return HTTP 200 OK if found, else HTTP 404 Not Found.
     * Example Success Response:
     * {
     *   "message": "Book found",
     *   "book": { "id": "B001", "title": "Spring Boot in Action", "author": "Craig Walls" }
     * }
     * Example Error Response:
     * { "error": "Book not found" }
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable String bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        Map<String, Object> response = new HashMap<>();

        if (book.isPresent()) {
            response.put("message", "Book found");
            response.put("book", book.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    /**
     * Searches books by title (case-insensitive).
     * @param title The title or keyword to search for.
     * @return HTTP 200 OK with matching books.
     * Example Response:
     * {
     *   "count": 1,
     *   "books": [ { "id": "B001", "title": "Spring Boot in Action", "author": "Craig Walls" } ]
     * }
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        Map<String, Object> response = new HashMap<>();
        response.put("count", books.size());
        response.put("books", books);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing book.
     * @param bookId The ID of the book to update.
     * @param book The updated book object.
     * @return HTTP 200 OK with the updated book details.
     * Example Response:
     * {
     *   "message": "Book updated successfully",
     *   "book": { "id": "B001", "title": "Spring Boot in Action", "author": "Craig Walls" }
     * }
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable String bookId, @Valid @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(bookId, book);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book updated successfully");
        response.put("book", updatedBook);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a book by its ID.
     * @param bookId The unique ID of the book to delete.
     * @return HTTP 200 OK if deleted, else HTTP 404 Not Found.
     * Example Success Response:
     * { "message": "Book deleted successfully" }
     * Example Error Response:
     * { "error": "Book not found" }
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable String bookId) {
        boolean deleted = bookService.deleteBook(bookId);
        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "Book deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
