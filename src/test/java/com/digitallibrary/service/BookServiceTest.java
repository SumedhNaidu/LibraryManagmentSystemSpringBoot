package com.digitallibrary.service;

import com.digitallibrary.model.Book;
import com.digitallibrary.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book("B001", "Spring Boot in Action", "Craig Walls", "Technical", true);
        book2 = new Book("B002", "Java Programming", "John Smith", "Technical", true);
    }

    @Test
    void testAddBook_Success() {
        when(bookRepository.existsByBookId(book1.getBookId())).thenReturn(false);
        when(bookRepository.save(book1)).thenReturn(book1);

        Book addedBook = bookService.addBook(book1);

        assertNotNull(addedBook, "Added book should not be null");
        assertEquals(book1.getBookId(), addedBook.getBookId(), "Book ID should match");
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    void testAddBook_DuplicateBookId_ThrowsException() {
        when(bookRepository.existsByBookId(book1.getBookId())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book1));

        assertEquals("Book with ID B001 already exists.", exception.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size(), "There should be 2 books");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Exists() {
        when(bookRepository.findByBookId("B001")).thenReturn(Optional.of(book1));

        Optional<Book> foundBook = bookService.getBookById("B001");

        assertTrue(foundBook.isPresent(), "Book should be found");
        assertEquals(book1.getBookId(), foundBook.get().getBookId());
        verify(bookRepository, times(1)).findByBookId("B001");
    }

    @Test
    void testGetBookById_NotExists() {
        when(bookRepository.findByBookId("B999")).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.getBookById("B999");

        assertFalse(foundBook.isPresent(), "Book should not be found");
        verify(bookRepository, times(1)).findByBookId("B999");
    }

    @Test
    void testUpdateBook_Success() {
        Book updatedDetails = new Book(null, "Updated Title", "Updated Author", "Updated Genre", false);

        when(bookRepository.findByBookId("B001")).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book updatedBook = bookService.updateBook("B001", updatedDetails);

        assertNotNull(updatedBook, "Updated book should not be null");
        assertEquals("Updated Title", updatedBook.getTitle(), "Title should be updated");
        assertEquals("Updated Author", updatedBook.getAuthor(), "Author should be updated");
        assertEquals("Updated Genre", updatedBook.getGenre(), "Genre should be updated");
        assertFalse(updatedBook.isAvailable(), "Availability should be updated to false");

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook_BookNotFound() {
        when(bookRepository.findByBookId("B999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook("B999", new Book());
        });

        assertEquals("Book with ID B999 not found.", exception.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepository.existsByBookId("B001")).thenReturn(true);
        when(bookRepository.deleteByBookId("B001")).thenReturn(1);

        boolean deleted = bookService.deleteBook("B001");

        assertTrue(deleted, "Book should be successfully deleted");
        verify(bookRepository, times(1)).deleteByBookId("B001");
    }

    @Test
    void testDeleteBook_NotExists() {
        when(bookRepository.existsByBookId("B999")).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook("B999"));

        assertEquals("Book with ID B999 not found.", exception.getMessage());
        verify(bookRepository, never()).deleteByBookId(anyString());
    }

    @Test
    void testSearchBooksByTitle() {
        when(bookRepository.findByTitleContainingIgnoreCase("Spring")).thenReturn(Arrays.asList(book1));

        List<Book> foundBooks = bookService.searchBooksByTitle("Spring");

        assertEquals(1, foundBooks.size(), "Only 1 book should be found");
        assertEquals("Spring Boot in Action", foundBooks.get(0).getTitle(), "Title should match");

        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Spring");
    }

    @Test
    void testGetAvailableBooks() {
        when(bookRepository.findByAvailable(true)).thenReturn(Arrays.asList(book1, book2));

        List<Book> availableBooks = bookService.getAvailableBooks();

        assertEquals(2, availableBooks.size(), "Both books should be available");
        verify(bookRepository, times(1)).findByAvailable(true);
    }

    @Test
    void testGetBooksByGenre() {
        when(bookRepository.findByGenre("Technical")).thenReturn(Arrays.asList(book1, book2));

        List<Book> technicalBooks = bookService.getBooksByGenre("Technical");

        assertEquals(2, technicalBooks.size(), "Both books should be of 'Technical' genre");
        verify(bookRepository, times(1)).findByGenre("Technical");
    }
}
